package com.alaazameldev.backendapi.controllers;

import com.alaazameldev.backendapi.domain.dtos.PostDto;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.mappers.PostMapper;
import com.alaazameldev.backendapi.services.PostService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService service;
  private final PostMapper postMapper;

  @GetMapping
  public ResponseEntity<List<PostDto>> getPosts(
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId
  ) {
    List<Post> posts = service.listPosts(categoryId, tagId);
    List<PostDto> parsedPosts = posts.stream()
        .map(postMapper::toDto)
        .toList();
    return ResponseEntity.ok(parsedPosts);
  }

}

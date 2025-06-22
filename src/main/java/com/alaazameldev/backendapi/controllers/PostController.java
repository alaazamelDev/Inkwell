package com.alaazameldev.backendapi.controllers;

import com.alaazameldev.backendapi.domain.dtos.CreatePostDto;
import com.alaazameldev.backendapi.domain.dtos.CreatePostRequest;
import com.alaazameldev.backendapi.domain.dtos.PostDto;
import com.alaazameldev.backendapi.domain.dtos.UpdatePostDto;
import com.alaazameldev.backendapi.domain.dtos.UpdatePostRequest;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.domain.entities.User;
import com.alaazameldev.backendapi.mappers.PostMapper;
import com.alaazameldev.backendapi.services.PostService;
import com.alaazameldev.backendapi.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService service;
  private final UserService userService;
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

  @GetMapping(path = "/drafts")
  public ResponseEntity<List<PostDto>> getDraftPosts(@RequestAttribute("userId") UUID userId) {

    // first, load the current user
    User loggedInUser = userService.getUserById(userId);

    // fetch draft posts for that user
    List<Post> posts = service.getDraftPosts(loggedInUser);
    List<PostDto> parsedPosts = posts.stream()
        .map(postMapper::toDto)
        .toList();

    return ResponseEntity.ok(parsedPosts);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
    Post post = service.getPost(id);
    PostDto dto = postMapper.toDto(post);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<PostDto> createPost(
      @RequestAttribute() UUID userId,
      @Valid @RequestBody CreatePostRequest request
  ) {

    // first, load the current user
    User loggedInUser = userService.getUserById(userId);

    // map to dto
    CreatePostDto postDto = postMapper.toCreateDto(request);
    Post createdPost = service.createPost(loggedInUser, postDto);
    PostDto mappedPost = postMapper.toDto(createdPost);

    return new ResponseEntity<>(mappedPost, HttpStatus.CREATED);
  }


  @PutMapping(path = "/{id}")
  public ResponseEntity<PostDto> updatePost(
      @PathVariable UUID id,
      @Valid @RequestBody UpdatePostRequest request
  ) {

    UpdatePostDto postDto = postMapper.toUpdateDto(request);
    Post updatedPost = service.updatePost(id, postDto);

    // map result
    PostDto mappedPost = postMapper.toDto(updatedPost);
    return ResponseEntity.ok(mappedPost);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable UUID id){
    service.deletePost(id);
    return ResponseEntity.noContent().build();
  }
}

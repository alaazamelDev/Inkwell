package com.alaazameldev.backendapi.controllers;

import com.alaazameldev.backendapi.domain.dtos.CreateTagsRequest;
import com.alaazameldev.backendapi.domain.dtos.TagDto;
import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.mappers.TagMapper;
import com.alaazameldev.backendapi.services.TagService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagMapper tagMapper;
  private final TagService service;


  @GetMapping
  public ResponseEntity<List<TagDto>> getTags() {
    List<Tag> tags = service.listTags();
    List<TagDto> parsedTags = tags.stream()
        .map(tagMapper::toDto)
        .toList();
    return ResponseEntity.ok(parsedTags);
  }

  @PostMapping
  public ResponseEntity<List<TagDto>> createTags(@Valid @RequestBody CreateTagsRequest request) {

    // create them
    List<Tag> createdTags = service.createTags(request.names());
    List<TagDto> parsedTags = createdTags.stream()
        .map(tagMapper::toDto)
        .toList();
    return new ResponseEntity<>(parsedTags, HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
    service.deleteTag(id);
    return null;
  }
}

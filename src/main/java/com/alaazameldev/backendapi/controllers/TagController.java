package com.alaazameldev.backendapi.controllers;

import com.alaazameldev.backendapi.domain.dtos.TagDto;
import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.mappers.TagMapper;
import com.alaazameldev.backendapi.services.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}

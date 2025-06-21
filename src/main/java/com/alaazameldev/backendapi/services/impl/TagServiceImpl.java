package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.exceptions.NotFoundException;
import com.alaazameldev.backendapi.repositories.TagRepository;
import com.alaazameldev.backendapi.services.TagService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository repository;

  @Override
  public List<Tag> listTags() {
    return repository.findAllWithPostCount();
  }

  @Override
  public List<Tag> createTags(Set<String> tagNames) {

    // get existing names
    Set<String> existingTagNames = repository.findByNameIn(tagNames)
        .stream()
        .map(Tag::getName)
        .collect(Collectors.toSet());

    Set<String> newTagNamesToCreate = new HashSet<>(tagNames);
    newTagNamesToCreate.removeAll(existingTagNames);

    List<Tag> newTags = newTagNamesToCreate.stream()
        .map(name -> Tag.builder()
            .name(name)
            .build())
        .toList();

    if (newTags.isEmpty()) {
      return new ArrayList<>();
    }

    return repository.saveAll(newTags);
  }

  @Override
  public void deleteTag(UUID id) {
    // check if there are posts associated
    Tag tag = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Tag not found with ID: " + id));

    if (!tag.getPosts().isEmpty()){
      throw new IllegalArgumentException("Tag has posts associated with it");
    }

    repository.deleteById(id);
  }
}

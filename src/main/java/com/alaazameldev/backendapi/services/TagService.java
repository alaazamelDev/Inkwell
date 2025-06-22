package com.alaazameldev.backendapi.services;

import com.alaazameldev.backendapi.domain.entities.Tag;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {

  Tag getTagById(UUID id);

  List<Tag> listTags();

  List<Tag> createTags(Set<String> tagNames);

  void deleteTag(UUID id);

  List<Tag> getTagsByIds(Set<UUID> ids);
}

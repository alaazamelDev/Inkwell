package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.repositories.TagRepository;
import com.alaazameldev.backendapi.services.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository repository;

  @Override
  public List<Tag> listTags() {
    return repository.findAll();
  }
}

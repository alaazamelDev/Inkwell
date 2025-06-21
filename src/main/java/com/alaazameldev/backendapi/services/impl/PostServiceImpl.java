package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.domain.entities.Category;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.domain.enums.PostStatus;
import com.alaazameldev.backendapi.repositories.PostRepository;
import com.alaazameldev.backendapi.services.CategoryService;
import com.alaazameldev.backendapi.services.PostService;
import com.alaazameldev.backendapi.services.TagService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final CategoryService categoryRepository;
  private final PostRepository repository;
  private final TagService tagRepository;


  @Override
  @Transactional(readOnly = true)
  public List<Post> listPosts(UUID categoryId, UUID tagId) {

    if (categoryId != null && tagId != null) {
      Category category = categoryRepository.getCategoryById(categoryId);
      Tag tag = tagRepository.getTagById(tagId);
      return repository.findAllByStatusAndCategoryAndTagsContaining(
          PostStatus.PUBLISHED,
          category,
          tag
      );
    }

    if (categoryId != null) {
      Category category = categoryRepository.getCategoryById(categoryId);
      return repository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
    }

    if (tagId != null) {
      Tag tag = tagRepository.getTagById(tagId);
      return repository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
    }

    return repository.findAllByStatus(PostStatus.PUBLISHED);
  }
}

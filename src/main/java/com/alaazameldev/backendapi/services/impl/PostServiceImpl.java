package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.domain.dtos.CreatePostDto;
import com.alaazameldev.backendapi.domain.dtos.UpdatePostDto;
import com.alaazameldev.backendapi.domain.entities.Category;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.domain.entities.User;
import com.alaazameldev.backendapi.domain.enums.PostStatus;
import com.alaazameldev.backendapi.repositories.PostRepository;
import com.alaazameldev.backendapi.services.CategoryService;
import com.alaazameldev.backendapi.services.PostService;
import com.alaazameldev.backendapi.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final CategoryService categoryService;
  private final PostRepository repository;
  private final TagService tagService;

  private static final int WORD_PER_MIN = 200;

  @Override
  public Post getPost(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Post not found with ID : " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Post> listPosts(UUID categoryId, UUID tagId) {

    if (categoryId != null && tagId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      Tag tag = tagService.getTagById(tagId);
      return repository.findAllByStatusAndCategoryAndTagsContaining(
          PostStatus.PUBLISHED,
          category,
          tag
      );
    }

    if (categoryId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      return repository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
    }

    if (tagId != null) {
      Tag tag = tagService.getTagById(tagId);
      return repository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
    }

    return repository.findAllByStatus(PostStatus.PUBLISHED);
  }

  @Override
  public List<Post> getDraftPosts(User user) {
    return repository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
  }

  @Override
  public Post createPost(User user, CreatePostDto postDto) {

    // get the category by id
    Category category = categoryService.getCategoryById(postDto.categoryId());

    // get tags
    List<Tag> tags = tagService.getTagsByIds(postDto.tagIds());

    Post newPost = Post.builder()
        .title(postDto.title())
        .content(postDto.content())
        .status(postDto.status())
        .author(user)
        .readingTime(calculateReadingTime(postDto.content()))
        .category(category)
        .tags(new HashSet<>(tags))
        .build();

    return repository.save(newPost);
  }

  @Override
  @Transactional
  public Post updatePost(UUID id, UpdatePostDto postDto) {

    Post post = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Post does not exist with ID : " + id));

    post.setTitle(postDto.title());
    post.setContent(postDto.content());
    post.setStatus(postDto.status());
    post.setReadingTime(calculateReadingTime(postDto.content()));

    UUID postDtoCategoryId = postDto.categoryId();
    if (!post.getCategory().getId().equals(postDtoCategoryId)) {
      Category category = categoryService.getCategoryById(postDto.categoryId());
      post.setCategory(category);
    }

    // get the existing tag ids set
    Set<UUID> existingTagIds = post.getTags().stream()
        .map(Tag::getId)
        .collect(Collectors.toSet());

    Set<UUID> updatedTagIds = postDto.tagIds();
    if (!existingTagIds.equals(updatedTagIds)) {
      List<Tag> updatedTags = tagService.getTagsByIds(updatedTagIds);
      post.setTags(new HashSet<>(updatedTags));
    }

    return repository.save(post);
  }

  @Override
  public void deletePost(UUID id) {
    Post post = getPost(id);
    repository.delete(post);
  }

  private Integer calculateReadingTime(String content) {
    if (content == null || content.isEmpty()) {
      return 0;
    }
    int wordCount = content.trim().split("\\s").length;
    return (int) Math.ceil((double) wordCount / WORD_PER_MIN);
  }
}

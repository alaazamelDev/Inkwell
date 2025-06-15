package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.domain.entities.Category;
import com.alaazameldev.backendapi.exceptions.NotFoundException;
import com.alaazameldev.backendapi.repositories.CategoryRepository;
import com.alaazameldev.backendapi.services.CategoryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

  private final CategoryRepository repository;

  @Override
  public List<Category> listCategories() {
    return repository.findAllWithPostCount();
  }

  @Override
  public Category createCategory(Category category) {
    if (repository.existsByNameIgnoreCase(category.getName())) {
      throw new IllegalArgumentException(
          "Category already exists with name: " + category.getName());
    }
    return repository.save(category);
  }

  @Override
  public void deleteCategory(UUID id) {

    Category category = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));

    if (!category.getPosts().isEmpty()) {
      throw new IllegalArgumentException("Category has posts associated with it");
    }
    repository.deleteById(id);
  }
}

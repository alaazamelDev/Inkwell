package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.domain.entities.Category;
import com.alaazameldev.backendapi.repositories.CategoryRepository;
import com.alaazameldev.backendapi.services.CategoryService;
import java.util.List;
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
}

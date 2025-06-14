package com.alaazameldev.backendapi.services;

import com.alaazameldev.backendapi.domain.entities.Category;
import java.util.List;

public interface CategoryService {

  List<Category> listCategories();
  Category createCategory(Category category);
}

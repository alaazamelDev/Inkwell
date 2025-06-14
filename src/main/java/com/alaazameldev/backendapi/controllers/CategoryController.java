package com.alaazameldev.backendapi.controllers;

import com.alaazameldev.backendapi.domain.dtos.CategoryDto;
import com.alaazameldev.backendapi.domain.dtos.CreateCategoryRequest;
import com.alaazameldev.backendapi.domain.entities.Category;
import com.alaazameldev.backendapi.mappers.CategoryMapper;
import com.alaazameldev.backendapi.services.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService service;
  private final CategoryMapper mapper;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> listCategories() {

    List<CategoryDto> categories = service.listCategories()
        .stream()
        .map(mapper::toDto)
        .toList();
    return ResponseEntity.ok(categories);
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(
      @Valid @RequestBody CreateCategoryRequest request
  ) {
    Category categoryToCrete = mapper.toEntity(request);
    Category createdCategory = service.createCategory(categoryToCrete);
    CategoryDto dto = mapper.toDto(createdCategory);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }
}

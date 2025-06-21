package com.alaazameldev.backendapi.mappers;

import com.alaazameldev.backendapi.domain.dtos.CategoryDto;
import com.alaazameldev.backendapi.domain.dtos.CreateCategoryRequest;
import com.alaazameldev.backendapi.domain.entities.Category;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.domain.enums.PostStatus;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

  @Mapping(
      target = "postCount",
      source = "posts",
      qualifiedByName = "calculatePostCount"
  )
  CategoryDto toDto(Category category);

  Category toEntity(CreateCategoryRequest request);

  @Named("calculatePostCount")
  default long calculatePostCount(List<Post> posts) {
    if (posts == null) {
      return 0;
    }
    return posts.stream()
        .filter(post -> post.getStatus().equals(PostStatus.PUBLISHED))
        .count();
  }
}

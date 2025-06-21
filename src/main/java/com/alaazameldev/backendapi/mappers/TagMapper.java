package com.alaazameldev.backendapi.mappers;

import com.alaazameldev.backendapi.domain.dtos.TagDto;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.domain.enums.PostStatus;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

  @Mapping(
      source = "posts",
      target = "postCount",
      qualifiedByName = "calculatePostCount"
  )
  TagDto toDto(Tag tag);

  @Named("calculatePostCount")
  default long calculatePostCount(Set<Post> posts) {
    return posts.stream()
        .filter(post -> post.getStatus().equals(PostStatus.PUBLISHED))
        .count();
  }
}

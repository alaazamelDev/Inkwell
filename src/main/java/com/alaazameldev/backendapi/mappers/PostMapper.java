package com.alaazameldev.backendapi.mappers;

import com.alaazameldev.backendapi.domain.dtos.PostDto;
import com.alaazameldev.backendapi.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  PostDto toDto(Post post);
}

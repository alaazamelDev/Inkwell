package com.alaazameldev.backendapi.mappers;

import com.alaazameldev.backendapi.domain.dtos.CreatePostDto;
import com.alaazameldev.backendapi.domain.dtos.CreatePostRequest;
import com.alaazameldev.backendapi.domain.dtos.PostDto;
import com.alaazameldev.backendapi.domain.dtos.UpdatePostDto;
import com.alaazameldev.backendapi.domain.dtos.UpdatePostRequest;
import com.alaazameldev.backendapi.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  PostDto toDto(Post post);

  CreatePostDto toCreateDto(CreatePostRequest request);

  UpdatePostDto toUpdateDto(UpdatePostRequest request);
}

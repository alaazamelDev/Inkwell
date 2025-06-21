package com.alaazameldev.backendapi.domain.dtos;

import com.alaazameldev.backendapi.domain.enums.PostStatus;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record PostDto(
    UUID id,
    String title,
    String content,
    PostStatus status,
    Integer readingTime,
    UserDto author,
    CategoryDto category,
    Set<TagDto> tags,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

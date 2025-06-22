package com.alaazameldev.backendapi.domain.dtos;

import com.alaazameldev.backendapi.domain.enums.PostStatus;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdatePostDto(
    UUID id,
    String title,
    String content,
    UUID categoryId,
    Set<UUID> tagIds,
    PostStatus status
) {

}

package com.alaazameldev.backendapi.domain.dtos;

import java.util.UUID;

public record TagDto(
    UUID id,
    String name,
    Integer postsCount
) {

}

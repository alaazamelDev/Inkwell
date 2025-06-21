package com.alaazameldev.backendapi.domain.dtos;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserDto(
    UUID id,
    String name
) {

}

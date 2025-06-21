package com.alaazameldev.backendapi.domain.dtos;

import java.util.UUID;
import lombok.Builder;

@Builder
public record CategoryDto(UUID id, String name, Integer postCount) {

}

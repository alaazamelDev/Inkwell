package com.alaazameldev.backendapi.domain.dtos;

import lombok.Builder;

@Builder
public record LoginRequest(
    String email,
    String password
) {

}

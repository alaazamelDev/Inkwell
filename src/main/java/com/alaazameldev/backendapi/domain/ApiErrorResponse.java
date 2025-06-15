package com.alaazameldev.backendapi.domain;

import java.util.List;
import lombok.Builder;

@Builder
public record ApiErrorResponse(
    int status,
    String message,
    List<FieldError> errors
) {

  @Builder
  public record FieldError(
      String field,
      String message
  ) {

  }
}

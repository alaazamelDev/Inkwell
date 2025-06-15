package com.alaazameldev.backendapi.controllers;

import com.alaazameldev.backendapi.domain.ApiErrorResponse;
import com.alaazameldev.backendapi.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice
public class ErrorController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
    log.error("Caught Exception", ex);
    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("An unexpected error occurred")
        .build();
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex     // The caught exception
  ) {
    log.error("Caught IllegalArgumentException", ex);
    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
    log.error("Caught NotFoundException", ex);

    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {

    log.info("Failed Login Attempt", ex);

    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.UNAUTHORIZED.value())
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }
}

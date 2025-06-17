package com.alaazameldev.backendapi.controllers;

import com.alaazameldev.backendapi.domain.dtos.AuthResponse;
import com.alaazameldev.backendapi.domain.dtos.LoginRequest;
import com.alaazameldev.backendapi.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authService;

  @PostMapping
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

    // authenticate first
    UserDetails userDetails = authService.authenticate(request.email(), request.password());

    // generate token
    String token = authService.generateToken(userDetails);
    AuthResponse response = AuthResponse.builder()
        .token(token)
        .expiresIn(86400)
        .build();
    return ResponseEntity.ok(response);
  }
}


package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authManager;
  private final UserDetailsService userDetailsService;

  @Value("${jwt.secret}")
  private String jwtSecretKey;

  @Override
  public UserDetails authenticate(String email, String password) {

    // generate auth token
    Authentication authRequest = new UsernamePasswordAuthenticationToken(email, password);
    authManager.authenticate(authRequest);

    return userDetailsService.loadUserByUsername(email);
  }

  @Override
  public String generateToken(UserDetails userDetails) {

    long jwtExpiryInMs = 86400000L;
    Map<String, Object> claims = new HashMap<>();

    return Jwts.builder()
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiryInMs))
        .signWith(getSigningKey())
        .compact();
  }

  @Override
  public UserDetails validateToken(String token) {
    String extractUsername = extractUsername(token);
    return userDetailsService.loadUserByUsername(extractUsername);
  }

  private String extractUsername(String token) {

    Claims claims = Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.getSubject();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = jwtSecretKey.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

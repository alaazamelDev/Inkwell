package com.alaazameldev.backendapi.security;

import com.alaazameldev.backendapi.services.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationService authService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    try {
      // first, extract the token
      String token = extractToken(request);

      if (token != null) {

        // second, validate it, and get authentication object.
        UserDetails userDetails = authService.validateToken(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );

        // inject authentication object into context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Optional (but recommended): add userId if possible
        if (userDetails instanceof BlogUserDetails) {
          UUID userId = ((BlogUserDetails) userDetails).getId();
          request.setAttribute("userId", userId);
        }
      }
    } catch (Exception e) {
      // Do not throw exceptions, just don't authenticate...
      log.warn("Received invalid auth token");
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}

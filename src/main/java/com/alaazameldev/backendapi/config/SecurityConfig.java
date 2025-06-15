package com.alaazameldev.backendapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable) // 1. Disable CSRF for Stateless APIs
        .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(requests -> requests

            .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/posts/").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/tags/").permitAll()

            .anyRequest().authenticated() // 3. Require authentication for all other requests
        );

    // 4. Add more configurations here later for session management, JWT filter, etc.

    return http.build();  // 5. Build and return the SecurityFilterChain
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }
}

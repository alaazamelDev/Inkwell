package com.alaazameldev.backendapi.config;

import com.alaazameldev.backendapi.domain.entities.User;
import com.alaazameldev.backendapi.repositories.UserRepository;
import com.alaazameldev.backendapi.security.JwtAuthenticationFilter;
import com.alaazameldev.backendapi.services.AuthenticationService;
import com.alaazameldev.backendapi.services.BlogUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetails(UserRepository repository, UserRepository userRepository) {
    BlogUserDetailsService blogUserDetailsService = new BlogUserDetailsService(repository);

    String email = "user@test.com";
    userRepository.findByEmail(email).orElseGet(() -> {
      User user = User.builder()
          .email(email)
          .password(passwordEncoder().encode("password1234"))
          .name("User Test")
          .build();
      return userRepository.save(user);
    });

    return blogUserDetailsService;
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authService) {
    return new JwtAuthenticationFilter(authService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      JwtAuthenticationFilter authenticationFilter
  ) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable) // 1. Disable CSRF for Stateless APIs
        .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()

            .anyRequest().authenticated() // 3. Require authentication for all other requests
        ).addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

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

package com.alaazameldev.backendapi.services;

import com.alaazameldev.backendapi.domain.entities.User;
import com.alaazameldev.backendapi.repositories.UserRepository;
import com.alaazameldev.backendapi.security.BlogUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    // load user from db
    User user = repository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    //
    return new BlogUserDetails(user);
  }
}

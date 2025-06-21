package com.alaazameldev.backendapi.services.impl;

import com.alaazameldev.backendapi.domain.entities.User;
import com.alaazameldev.backendapi.repositories.UserRepository;
import com.alaazameldev.backendapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  @Override
  public User getUserById(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found with ID : " + id));
  }
}

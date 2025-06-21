package com.alaazameldev.backendapi.services;

import com.alaazameldev.backendapi.domain.entities.User;
import java.util.UUID;

public interface UserService {

  User getUserById(UUID id);

}

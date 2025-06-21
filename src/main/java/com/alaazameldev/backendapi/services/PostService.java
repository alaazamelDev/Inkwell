package com.alaazameldev.backendapi.services;

import com.alaazameldev.backendapi.domain.entities.Post;
import java.util.List;
import java.util.UUID;

public interface PostService {

  List<Post> listPosts(UUID categoryId, UUID tagId);
}

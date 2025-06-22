package com.alaazameldev.backendapi.services;

import com.alaazameldev.backendapi.domain.dtos.CreatePostDto;
import com.alaazameldev.backendapi.domain.dtos.UpdatePostDto;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.domain.entities.User;
import java.util.List;
import java.util.UUID;

public interface PostService {

  Post getPost(UUID id);

  List<Post> listPosts(UUID categoryId, UUID tagId);

  List<Post> getDraftPosts(User user);

  Post createPost(User user, CreatePostDto postDto);

  Post updatePost(UUID id, UpdatePostDto postDto);
}

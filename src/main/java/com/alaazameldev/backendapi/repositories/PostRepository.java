package com.alaazameldev.backendapi.repositories;

import com.alaazameldev.backendapi.domain.entities.Category;
import com.alaazameldev.backendapi.domain.entities.Post;
import com.alaazameldev.backendapi.domain.entities.Tag;
import com.alaazameldev.backendapi.domain.entities.User;
import com.alaazameldev.backendapi.domain.enums.PostStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

  List<Post> findAllByStatusAndCategoryAndTagsContaining(
      PostStatus status,
      Category category,
      Tag tag
  );

  List<Post> findAllByStatusAndCategory(PostStatus status, Category category);

  List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);

  List<Post> findAllByStatus(PostStatus status);


  List<Post> findAllByAuthorAndStatus(User user, PostStatus postStatus);
}

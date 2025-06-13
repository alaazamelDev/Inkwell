package com.alaazameldev.backendapi.repositories;

import com.alaazameldev.backendapi.domain.entities.Post;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

}

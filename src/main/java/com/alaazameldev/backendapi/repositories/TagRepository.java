package com.alaazameldev.backendapi.repositories;

import com.alaazameldev.backendapi.domain.entities.Tag;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

}

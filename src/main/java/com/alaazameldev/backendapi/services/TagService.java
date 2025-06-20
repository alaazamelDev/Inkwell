package com.alaazameldev.backendapi.services;

import com.alaazameldev.backendapi.domain.entities.Tag;
import java.util.List;

public interface TagService {

  List<Tag> listTags();
}

package com.magadiflo.jdbc.app.repository;

import com.magadiflo.jdbc.app.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}

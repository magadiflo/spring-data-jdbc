package com.magadiflo.jdbc.app.repository;

import com.magadiflo.jdbc.app.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
}

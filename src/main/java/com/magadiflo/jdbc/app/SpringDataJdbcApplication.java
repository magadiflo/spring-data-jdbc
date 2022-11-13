package com.magadiflo.jdbc.app;

import com.magadiflo.jdbc.app.model.Author;
import com.magadiflo.jdbc.app.model.Comment;
import com.magadiflo.jdbc.app.model.Post;
import com.magadiflo.jdbc.app.repository.AuthorRepository;
import com.magadiflo.jdbc.app.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootApplication
public class SpringDataJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJdbcApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PostRepository postRepository, AuthorRepository authorRepository) {
        return args -> {
            Integer id = authorRepository.save(new Author(null, "Martín", "Díaz", "martin@gmail.com", "magadiflo")).id();
            AggregateReference<Author, Integer> author = AggregateReference.to(id);

            Post post = new Post("Hello, world!", "Welcome to my blog!", author);
            post.addComment(new Comment("Tinkler", "This is my first comment"));

            postRepository.save(post);
        };
    }

}

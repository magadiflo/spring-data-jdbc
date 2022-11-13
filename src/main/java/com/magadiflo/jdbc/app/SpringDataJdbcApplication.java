package com.magadiflo.jdbc.app;

import com.magadiflo.jdbc.app.model.Post;
import com.magadiflo.jdbc.app.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJdbcApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PostRepository postRepository) {
		return args -> {
			postRepository.save(new Post("Hello, world!", "Welcome to my blog!"));
		};
	}

}

package com.magadiflo.jdbc.app.controller;

import com.magadiflo.jdbc.app.model.Author;
import com.magadiflo.jdbc.app.model.Post;
import com.magadiflo.jdbc.app.model.dto.PostDetails;
import com.magadiflo.jdbc.app.repository.AuthorRepository;
import com.magadiflo.jdbc.app.repository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostController(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public Iterable<Post> finalAll() {
        return this.postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post findById(@PathVariable Integer id) {
        return this.postRepository.findById(id).orElseThrow();
    }

    @GetMapping(path = "/{id}/details")
    public PostDetails getPostDetails(@PathVariable Integer id) {
        Post post = this.postRepository.findById(id).orElseThrow();
        Author author = this.authorRepository.findById(post.getAuthor().getId()).orElseThrow();
        return new PostDetails(post, author);
    }
}

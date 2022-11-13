package com.magadiflo.jdbc.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Post {

    @Id
    //Como usamos JDBC, esta anotación es del paquete de springframework y no de javax.persistence como cuando usábamos JPA
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    private AggregateReference<Author, Integer> authorId;
    private Set<Comment> comments = new HashSet<>();

    public Post(String title, String content, AggregateReference<Author, Integer> authorId) {
        this.title = title;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
        this.authorId = authorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDateTime publishedOn) {
        this.publishedOn = publishedOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public AggregateReference<Author, Integer> getAuthorId() {
        return authorId;
    }

    public void setAuthorId(AggregateReference<Author, Integer> authorId) {
        this.authorId = authorId;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Post{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", publishedOn=").append(publishedOn);
        sb.append(", updatedOn=").append(updatedOn);
        sb.append('}');
        return sb.toString();
    }
}

package com.magadiflo.jdbc.app.model;

import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

public class Comment {

    private String name;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;

    @Transient
    private Post post;

    public Comment(String name, String content) {
        this.name = name;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("name='").append(name).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", publishedOn=").append(publishedOn);
        sb.append(", updatedOn=").append(updatedOn);
        sb.append('}');
        return sb.toString();
    }
}

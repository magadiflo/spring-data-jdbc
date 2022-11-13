package com.magadiflo.jdbc.app.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Post {

    @Id //Como usamos JDBC, esta anotación es del paquete de springframework y no de javax.persistence como cuando usábamos JPA
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    //Author
    // Comments

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
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

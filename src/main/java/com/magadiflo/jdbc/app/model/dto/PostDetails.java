package com.magadiflo.jdbc.app.model.dto;

import com.magadiflo.jdbc.app.model.Author;
import com.magadiflo.jdbc.app.model.Post;

public record PostDetails(Post post, Author author) {
}

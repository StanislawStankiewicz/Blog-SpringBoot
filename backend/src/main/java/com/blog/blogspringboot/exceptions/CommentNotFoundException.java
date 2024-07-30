package com.blog.blogspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommentNotFoundException extends ResponseStatusException {

    public CommentNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}

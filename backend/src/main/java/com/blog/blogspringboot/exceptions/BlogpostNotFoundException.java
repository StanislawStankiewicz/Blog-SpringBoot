package com.blog.blogspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BlogpostNotFoundException extends ResponseStatusException {

    public BlogpostNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}

package com.blog.blogspringboot.exceptions;

import org.springframework.web.server.ResponseStatusException;

public class ConflictException extends ResponseStatusException {

    public ConflictException(String reason) {
        super(org.springframework.http.HttpStatus.CONFLICT, reason);
    }
}

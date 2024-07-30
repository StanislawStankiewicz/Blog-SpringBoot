package com.blog.blogspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CredentialsException extends ResponseStatusException {

        public CredentialsException(String reason) {
            super(HttpStatus.UNAUTHORIZED, reason);
        }
}

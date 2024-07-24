package com.blog.blogspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class RegisterResponse {

    @JsonIgnore
    private final HttpStatus status;

    private final boolean success;

    private final String message;
}

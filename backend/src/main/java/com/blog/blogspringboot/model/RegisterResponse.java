package com.blog.blogspringboot.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private final String message;
}

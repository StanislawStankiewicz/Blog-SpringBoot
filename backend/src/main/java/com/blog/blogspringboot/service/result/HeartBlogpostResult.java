package com.blog.blogspringboot.service.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HeartBlogpostResult {
    private boolean success;

    private String message;

    private HttpStatus status;
}

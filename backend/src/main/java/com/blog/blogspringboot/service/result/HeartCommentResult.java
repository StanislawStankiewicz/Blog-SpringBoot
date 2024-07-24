package com.blog.blogspringboot.service.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HeartCommentResult {
    private boolean success;

    private Object message;

    private HttpStatus status;
}

package com.blog.blogspringboot.service.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class RegistrationResult {
    private boolean success;

    private Object message;
    @JsonIgnore
    private HttpStatus status;
}
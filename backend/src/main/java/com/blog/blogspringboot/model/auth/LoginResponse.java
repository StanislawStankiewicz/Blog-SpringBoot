package com.blog.blogspringboot.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Response object for login requests")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    private final boolean success;

    private final String message;

    private final String username;
}

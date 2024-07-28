package com.blog.blogspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@Schema(description = "Response object for registration requests")
public class RegisterResponse {

    @JsonIgnore
    private final HttpStatus status;

    @Schema(description = "Indicates whether the registration was successful")
    private final boolean success;

    @Schema(description = "Message providing additional information about the registration")
    private final String message;
}

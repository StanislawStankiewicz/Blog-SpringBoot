package com.blog.blogspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@Schema(description = "Response object for login requests")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    @JsonIgnore
    private HttpStatus status;

    @Schema(description = "Indicates whether the login was successful")
    private final boolean success;

    @Schema(description = "Message providing additional information about the login")
    private final String message;

    @Schema(description = "Access token for the user")
    private final String accessToken;
}

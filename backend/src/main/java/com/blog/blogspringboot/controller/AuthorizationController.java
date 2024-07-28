package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.model.LoginRequest;
import com.blog.blogspringboot.model.LoginResponse;
import com.blog.blogspringboot.model.RegisterRequest;
import com.blog.blogspringboot.model.RegisterResponse;
import com.blog.blogspringboot.service.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/auth/login")
    @Operation(summary = "Login with username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class),
                                    examples = @ExampleObject(value = "{ \"success\": true, \"message\": " +
                                            "\"Login successful.\", \"accessToken\": " +
                                            "\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInJvbGUiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE2MzIwNzU4NzMsImV4cCI6MTYzMjA3NjA3M30.1Z6\" }"))
    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": " +
                                            "\"Invalid username or password.\" }"))
                    })
    })
    public ResponseEntity<Object> login(@RequestBody @Validated LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        LoginResponse result = authorizationService.attemptLogin(username, password);

        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful registration",
                            content = {
                                        @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = RegisterResponse.class),
                                        examples = @ExampleObject(value = "{ \"success\": true, \"message\": " +
                                                "\"User registered successfully.\" }"))
                                        }),
            @ApiResponse(responseCode = "400", description = "Invalid password",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterResponse.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": " +
                                            "\"Password must be at least 8 characters long.\" }"))
                    }),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Username already in use", value = "{ \"success\": false, \"message\": \"Username already exists.\" }"),
                                            @ExampleObject(name = "Email already in use", value = "{ \"success\": false, \"message\": \"Email already exists.\" }")
                                    })
                    })
    })
    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@RequestBody @Validated RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        RegisterResponse result = authorizationService.attemptRegister(username, password, email);

        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }
}

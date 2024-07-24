package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.model.LoginRequest;
import com.blog.blogspringboot.model.LoginResponse;
import com.blog.blogspringboot.model.RegisterRequest;
import com.blog.blogspringboot.model.RegisterResponse;
import com.blog.blogspringboot.service.AuthorizationService;
import com.blog.blogspringboot.service.UserService;
import com.blog.blogspringboot.service.result.RegistrationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody @Validated LoginRequest request) {
        // TODO refactor like register
        System.out.println("login request: " + request);
        LoginResponse result = authorizationService.attemptLogin(request.getUsername(), request.getPassword());

        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

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

    @DeleteMapping("/auth/db")
    public ResponseEntity<Object> restartDatabase() {

    }
}

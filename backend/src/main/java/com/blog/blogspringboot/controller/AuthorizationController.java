package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.model.LoginRequest;
import com.blog.blogspringboot.model.LoginResponse;
import com.blog.blogspringboot.model.RegisterRequest;
import com.blog.blogspringboot.model.RegisterResponse;
import com.blog.blogspringboot.service.AuthorizationService;
import com.blog.blogspringboot.service.UserService;
import com.blog.blogspringboot.service.result.RegistrationResult;
import com.blog.blogspringboot.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    private final UserService userService;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        // TODO refactor like register
        return authorizationService.attemptLogin(request.getUsername(), request.getPassword());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        RegistrationResult result = authorizationService.attemptRegister(username, password, email);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/admin")
    public String admin(Principal principal) {
        return "Hello, " + principal.getName();
    }
}

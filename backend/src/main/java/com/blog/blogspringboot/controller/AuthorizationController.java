package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.model.LoginRequest;
import com.blog.blogspringboot.model.LoginResponse;
import com.blog.blogspringboot.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authorizationService.attemptLogin(request.getUsername(), request.getPassword());
    }

    @PostMapping("/admin")
    public String admin(Principal principal) {
        return "Hello, " + principal.getName();
    }
}

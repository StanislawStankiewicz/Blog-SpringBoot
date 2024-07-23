package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.model.LoginRequest;
import com.blog.blogspringboot.model.LoginResponse;
import com.blog.blogspringboot.security.UserPrincipal;
import com.blog.blogspringboot.security.jwt.JwtIssuer;
import com.blog.blogspringboot.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) throws Exception {
        return authorizationService.attemptLogin(request.getUsername(), request.getPassword());
    }

    @PostMapping("/admin")
    public String admin(Principal principal) {
        return "Hello, " + principal.getName();
    }
}

package com.blog.blogspringboot.service;

import com.blog.blogspringboot.entity.Role;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.model.LoginResponse;
import com.blog.blogspringboot.model.RegisterResponse;
import com.blog.blogspringboot.security.UserPrincipal;
import com.blog.blogspringboot.security.jwt.JwtIssuer;
import com.blog.blogspringboot.service.result.RegistrationResult;
import com.blog.blogspringboot.util.JsonUtil;
import com.blog.blogspringboot.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final JwtIssuer jwtIssuer;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public LoginResponse attemptLogin(String username, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            return LoginResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .success(false)
                    .message("Invalid username or password.")
                    .build();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);
        return LoginResponse.builder()
                .status(HttpStatus.OK)
                .success(true)
                .message("Login successful.")
                .accessToken(token).build();
    }

    public RegisterResponse attemptRegister(String username, String password, String email) {
        if (userService.getUserByUsername(username) != null) {
            return RegisterResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .success(false)
                    .message("Username already exists.")
                    .build();
        }
        if (!UserUtils.validatePassword()) {
            return RegisterResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("Password must be at least 8 characters long.")
                    .build();
        }
        String hashed_password = new BCryptPasswordEncoder().encode(password);
        User user = User.builder()
                .username(username)
                .password(hashed_password)
                .email(email)
                .build();
        // TODO roles should be enums
        user.assignRole(new Role(1, "ROLE_USER"));
        userService.saveUser(user);
        return RegisterResponse.builder()
                .status(HttpStatus.CREATED)
                .success(true)
                .build();
    }
}

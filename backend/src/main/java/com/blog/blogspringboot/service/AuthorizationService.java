package com.blog.blogspringboot.service;

import com.blog.blogspringboot.entity.Role;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.exceptions.ConflictException;
import com.blog.blogspringboot.exceptions.CredentialsException;
import com.blog.blogspringboot.model.auth.LoginResponse;
import com.blog.blogspringboot.security.UserPrincipal;
import com.blog.blogspringboot.security.jwt.JwtIssuer;
import com.blog.blogspringboot.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
            throw new CredentialsException("Invalid username or password.");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);
        return LoginResponse.builder()
                .accessToken(token).build();
    }

    public void attemptRegister(String username, String password, String email) {
        if (userService.getUserByUsername(username).isPresent()) {
            throw new ConflictException("Username already exists.");
        }
        if (userService.userExistsByEmail(email)) {
            throw new ConflictException("Email already exists.");
        }
        if (!UserUtils.validatePassword()) {
            throw new CredentialsException("Invalid password.");
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        User user = User.builder()
                .username(username)
                .password(hashedPassword)
                .email(email)
                .build();
        // TODO roles should be enums
        user.assignRole(new Role(1, "ROLE_USER"));
        userService.saveUser(user);
    }
}

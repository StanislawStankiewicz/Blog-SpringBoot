package com.blog.blogspringboot.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties properties;

    public String issue(int userId, String username, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.ofDays(1)))
                .withClaim("u", username)
                .withClaim("a", roles)
                .sign(Algorithm.HMAC256(properties.getSecret()));
    }
}

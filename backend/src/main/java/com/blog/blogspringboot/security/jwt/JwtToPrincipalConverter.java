package com.blog.blogspringboot.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blog.blogspringboot.security.UserPrincipal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtToPrincipalConverter implements Converter<DecodedJWT, UserDetails> {
    @Override
    public UserPrincipal convert(DecodedJWT jwt) {
        String username = jwt.getClaim("u").asString();
        List<String> roles = jwt.getClaim("a").asList(String.class);

        List<SimpleGrantedAuthority> authorities = roles != null ?
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        Claim claim = jwt.getClaim("a");
        if (claim.isNull() || claim.isMissing()) {
            return List.of();
        }
        return claim.asList(SimpleGrantedAuthority.class);
    }
}

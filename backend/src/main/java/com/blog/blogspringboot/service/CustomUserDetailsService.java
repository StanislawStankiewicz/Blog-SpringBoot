package com.blog.blogspringboot.service;

import com.blog.blogspringboot.entity.Role;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<Role> roles = user.getRoles();
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Replace getRoleName() with the actual method/field in Role that represents the authority
                .collect(Collectors.toSet());

        return UserPrincipal.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .authorities(authorities)
                .password(user.getPassword())
                .build();
    }

}

package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.security.UserPrincipal;
import com.blog.blogspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@AuthenticationPrincipal UserPrincipal principal, @PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}

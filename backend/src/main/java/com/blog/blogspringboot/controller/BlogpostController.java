package com.blog.blogspringboot.controller;


import com.blog.blogspringboot.dto.BlogpostRequestDTO;
import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/api/blogposts")
public class BlogpostController {

    private final BlogpostService blogpostService;

    @Autowired
    public BlogpostController(BlogpostService blogpostService) {
        this.blogpostService = blogpostService;
    }

    @GetMapping
    public Page<Blogpost> getAllBlogposts(Pageable pageable,
                                          @RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return blogpostService.getAllBlogpostsByUserId(userId, pageable);
        }
        return blogpostService.getAllBlogposts(pageable);
    }

    @PostMapping
    public ResponseEntity<Object> createBlogpost(Principal principal, @RequestBody BlogpostRequestDTO blogpost) {
        Blogpost createdBlogpost = blogpostService.createBlogpost(blogpost, principal.getName());
        return ResponseEntity.ok(createdBlogpost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBlogpostById(@PathVariable int id) {
        Blogpost blogpost = blogpostService.getBlogpostById(id);
        if (blogpost == null) {
            return ResponseEntity
                    .status(400)
                    .body(Collections.singletonMap("message", "No blogpost found with ID " + id));
        }
        return ResponseEntity.ok(blogpost);
    }
}

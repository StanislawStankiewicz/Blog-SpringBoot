package com.blog.blogspringboot.controller;


import com.blog.blogspringboot.dto.BlogpostRequestDTO;
import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.exceptions.BlogpostNotFoundException;
import com.blog.blogspringboot.service.BlogpostService;
import com.blog.blogspringboot.model.blogpost.HeartBlogpostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blogposts")
public class BlogpostController {

    private final BlogpostService blogpostService;

    @PostMapping
    public ResponseEntity<Object> createBlogpost(Principal principal, @RequestBody BlogpostRequestDTO blogpost) {
        Blogpost createdBlogpost = blogpostService.createBlogpost(blogpost, principal.getName());
        return ResponseEntity.ok(createdBlogpost);
    }

    @GetMapping
    public Page<Blogpost> getAllBlogposts(Pageable pageable,
                                          @RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return blogpostService.getAllBlogpostsByUserId(userId, pageable);
        }
        return blogpostService.getAllBlogposts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBlogpostById(@PathVariable int id) {
        Blogpost blogpost = blogpostService.getBlogpostById(id)
                .orElseThrow(() -> new BlogpostNotFoundException("Blogpost not found"));

        return ResponseEntity.ok(blogpost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBlogpost(@PathVariable int id, Principal principal) {
        blogpostService.deleteBlogpost(id, principal.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/heart")
    public ResponseEntity<HeartBlogpostResponse> heartBlogpost(@PathVariable int id, Principal principal) {
        boolean hearted = blogpostService.heartBlogpost(id, principal.getName());
        HeartBlogpostResponse response = HeartBlogpostResponse.builder()
                .hearted(hearted)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/heart")
    public ResponseEntity<HeartBlogpostResponse> getHeartBlogpost(@PathVariable int id, Principal principal) {
        boolean hearted = blogpostService.getHeartBlogpost(id, principal.getName());
        HeartBlogpostResponse response = HeartBlogpostResponse.builder()
                .hearted(hearted)
                .build();
        return ResponseEntity.ok(response);
    }
}

package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.entity.Comment;
import com.blog.blogspringboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public Page<Comment> getAllComments(Pageable pageable,
                                          @RequestParam(required = false) Integer userId,
                                          @RequestParam(required = false) Integer blogpostId) {
        if (userId != null && blogpostId != null) {
            return commentService.getAllCommentsByUserIdAndBlogpostId(userId, blogpostId, pageable);
        }
        if (blogpostId != null) {
            return commentService.getAllCommentsByBlogpostId(blogpostId, pageable);
        }
        if (userId != null) {
            return commentService.getAllCommentsByUserId(userId, pageable);
        }
        return commentService.getAllComments(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable int id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity
                    .status(400)
                    .body(Collections.singletonMap("message", "No comment found with ID " + id));
        }
        return ResponseEntity.ok(comment);
    }
}

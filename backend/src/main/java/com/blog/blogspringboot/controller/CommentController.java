package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.dto.CommentRequestDTO;
import com.blog.blogspringboot.entity.Comment;
import com.blog.blogspringboot.service.CommentService;
import com.blog.blogspringboot.model.HeartCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
        // TODO implement JpaSpecificationExecutor in the future
        if (userId != null && blogpostId != null) {
            return commentService.getAllCommentsByUserIdAndBlogpostId(userId, blogpostId, pageable);
        }
        if (blogpostId != null ) {
            return commentService.getAllCommentsByBlogpostId(blogpostId, pageable);
        }
        if (userId != null) {
            return commentService.getAllCommentsByUserId(userId, pageable);
        }
        return commentService.getAllComments(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable int id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity
                    .status(400)
                    .body(Collections.singletonMap("message", "No comment found with ID " + id));
        }
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Object> createComment(@RequestBody CommentRequestDTO comment, Principal principal) {
        Comment createdComment = commentService.createComment(comment, principal.getName());
        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable int id, Principal principal) {
        HttpStatus status = commentService.deleteComment(id, principal);
        return ResponseEntity.status(status).build();
    }

    @PostMapping("/{id}/heart")
    public HeartCommentResponse heartComment(@PathVariable int id, Principal principal) {
        return commentService.heartComment(id, principal.getName());
    }

    @GetMapping("/{id}/heart")
    public HeartCommentResponse getHeartComment(@PathVariable int id, Principal principal) {
        return commentService.getHeartComment(id, principal.getName());
    }
}

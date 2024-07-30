package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.dto.CommentRequestDTO;
import com.blog.blogspringboot.entity.Comment;
import com.blog.blogspringboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.security.Principal;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Get all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of comments",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class),
                                    examples = @ExampleObject(value = "{ \"content\": [], \"totalElements\": 0, \"totalPages\": 0, \"size\": 20, \"number\": 0, \"numberOfElements\": 0 }"))
                    })
    })
    @GetMapping
    public Page<Comment> getAllComments(Pageable pageable,
                                        @RequestParam(required = false) Integer blogpostId) {
        if (blogpostId != null) {
            return commentService.getAllCommentsByBlogpostId(blogpostId, pageable);
        }
        return commentService.getAllComments(pageable);
    }

    @Operation(summary = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class),
                                    examples = @ExampleObject(value = "{ \"success\": true, \"message\": \"Comment created successfully.\" }"))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": \"Invalid input data.\" }"))
                    })
    })
    @PostMapping
    public ResponseEntity<Object> createComment(Principal principal, @RequestBody CommentRequestDTO comment) {
        Comment createdComment = commentService.createComment(comment, principal.getName());
        return ResponseEntity.ok(createdComment);
    }

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": \"Comment not found.\" }"))
                    })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable int id, Principal principal) {
        commentService.deleteComment(id, principal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

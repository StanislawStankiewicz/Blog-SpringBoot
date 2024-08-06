package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.model.comment.PostCommentRequest;
import com.blog.blogspringboot.entity.Comment;
import com.blog.blogspringboot.model.comment.HeartCommentResponse;
import com.blog.blogspringboot.service.CommentService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

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
    public ResponseEntity<Object> createComment(Principal principal, @RequestBody PostCommentRequest comment) {
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

    @Operation(summary = "Heart a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment hearted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HeartCommentResponse.class),
                                    examples = @ExampleObject(value = "{ \"hearted\": true }"))
                    }),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HeartCommentResponse.class),
                                    examples = @ExampleObject(value = "{ \"hearted\": false }"))
                    })
    })
    @PostMapping("/{id}/heart")
    public ResponseEntity<HeartCommentResponse> heartComment(@PathVariable int id, Principal principal) {
        boolean hearted =  commentService.heartComment(id, principal.getName());
        HeartCommentResponse response = HeartCommentResponse.builder()
                .hearted(hearted)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get heart status of a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Heart status retrieved successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HeartCommentResponse.class),
                                    examples = @ExampleObject(value = "{ \"hearted\": true }"))
                    }),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HeartCommentResponse.class),
                                    examples = @ExampleObject(value = "{ \"hearted\": false }"))
                    })
    })
    @GetMapping("/{id}/heart")
    public ResponseEntity<HeartCommentResponse> getHeartComment(@PathVariable int id, Principal principal) {
        boolean hearted = commentService.getHeartComment(id, principal.getName());
        HeartCommentResponse response = HeartCommentResponse.builder()
                .hearted(hearted)
                .build();
        return ResponseEntity.ok(response);
    }
}

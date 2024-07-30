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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blogposts")
public class BlogpostController {

    private final BlogpostService blogpostService;

    @Operation(summary = "Create a new blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Blogpost.class),
                                    examples = @ExampleObject(value = "{ \"success\": true, \"message\": \"Blog post created successfully.\" }"))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Blogpost.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": \"Invalid input data.\" }"))
                    })
    })
    @PostMapping
    public ResponseEntity<Object> createBlogpost(Principal principal, @RequestBody BlogpostRequestDTO blogpost) {
        Blogpost createdBlogpost = blogpostService.createBlogpost(blogpost, principal.getName());
        return ResponseEntity.ok(createdBlogpost);
    }

    @Operation(summary = "Get all blog posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of blog posts",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class),
                                    examples = @ExampleObject(value = "{ \"content\": [], \"totalElements\": 0, \"totalPages\": 0, \"size\": 20, \"number\": 0, \"numberOfElements\": 0 }"))
                    })
    })
    @GetMapping
    public Page<Blogpost> getAllBlogposts(Pageable pageable, @RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return blogpostService.getAllBlogpostsByUserId(userId, pageable);
        }
        return blogpostService.getAllBlogposts(pageable);
    }

    @Operation(summary = "Get a blog post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post details",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Blogpost.class),
                                    examples = @ExampleObject(value = "{ \"id\": 1, \"title\": \"Sample Title\", \"content\": \"Sample Content\" }"))
                    }),
            @ApiResponse(responseCode = "404", description = "Blog post not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Blogpost.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": \"Blog post not found.\" }"))
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBlogpostById(@PathVariable int id) {
        Blogpost blogpost = blogpostService.getBlogpostById(id)
                .orElseThrow(() -> new BlogpostNotFoundException("Blogpost not found"));

        return ResponseEntity.ok(blogpost);
    }

    @Operation(summary = "Delete a blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Blog post deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Blog post not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Blogpost.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": \"Blog post not found.\" }"))
                    })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBlogpost(@PathVariable int id, Principal principal) {
        blogpostService.deleteBlogpost(id, principal.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

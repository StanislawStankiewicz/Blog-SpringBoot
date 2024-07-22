package com.blog.blogspringboot.controller;


import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blogposts")
public class BlogpostController {

    private final BlogpostService blogpostService;

    @Autowired
    public BlogpostController(BlogpostService blogpostService) {
        this.blogpostService = blogpostService;
    }

    @GetMapping
    public Page<Blogpost> getAllBlogposts(Pageable pageable) {
        return blogpostService.getAllBlogposts(pageable);
    }
}

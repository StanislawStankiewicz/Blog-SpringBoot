package com.blog.blogspringboot.service;

import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.repository.BlogpostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BlogpostService {

    private final BlogpostRepository blogpostRepository;

    @Autowired
    public BlogpostService(BlogpostRepository blogpostRepository) {
        this.blogpostRepository = blogpostRepository;
    }

    public Blogpost saveBlogpost(Blogpost blogpost) {
        return blogpostRepository.save(blogpost);
    }

    public Blogpost getBlogpostById(int id) {
        return blogpostRepository.findById(id).orElse(null);
    }

    public Page<Blogpost> getAllBlogposts(Pageable pageable) {
        return blogpostRepository.findAll(pageable);
    }

    public void deleteBlogpost(int id) {
        blogpostRepository.deleteById(id);
    }

    public Page<Blogpost> getAllBlogpostsByUserId(Integer userId, Pageable pageable) {
        return blogpostRepository.findAllByUserId(userId, pageable);
    }
}
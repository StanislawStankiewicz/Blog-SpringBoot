package com.blog.blogspringboot.service;

import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.repository.BlogpostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Blogpost> getBlogpostById(int id) {
        return blogpostRepository.findById(id);
    }

    public List<Blogpost> getAllBlogposts() {
        return blogpostRepository.findAll();
    }

    public void deleteBlogpost(int id) {
        blogpostRepository.deleteById(id);
    }

}
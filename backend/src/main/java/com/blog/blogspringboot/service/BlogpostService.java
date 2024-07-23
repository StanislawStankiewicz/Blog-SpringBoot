package com.blog.blogspringboot.service;

import com.blog.blogspringboot.dto.BlogpostRequestDTO;
import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.repository.BlogpostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogpostService {

    private final BlogpostRepository blogpostRepository;

    private final UserService userService;

    public Blogpost createBlogpost(BlogpostRequestDTO blogpostRequestDTO, String username) {
        User user = userService.getUserByUsername(username);
        Blogpost blogpost = Blogpost.builder()
                .user(user)
                .title(blogpostRequestDTO.getTitle())
                .content(blogpostRequestDTO.getContent())
                .hearts(0)
                .createdAt(new Date())
                .build();

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
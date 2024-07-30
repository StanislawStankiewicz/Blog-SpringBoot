package com.blog.blogspringboot.service;

import com.blog.blogspringboot.dto.BlogpostRequestDTO;
import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.exceptions.BlogpostNotFoundException;
import com.blog.blogspringboot.exceptions.ForbiddenException;
import com.blog.blogspringboot.exceptions.UserNotFoundException;
import com.blog.blogspringboot.repository.BlogpostRepository;
import com.blog.blogspringboot.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogpostService {

    private final BlogpostRepository blogpostRepository;

    private final UserService userService;

    public Blogpost createBlogpost(BlogpostRequestDTO blogpostRequestDTO, String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Blogpost blogpost = Blogpost.builder()
                .user(user)
                .title(blogpostRequestDTO.getTitle())
                .content(blogpostRequestDTO.getContent())
                .hearts(0)
                .createdAt(new Date())
                .build();

        return blogpostRepository.save(blogpost);
    }

    public Optional<Blogpost> getBlogpostById(int id) {
        return blogpostRepository.findById(id);
    }

    public Page<Blogpost> getAllBlogposts(Pageable pageable) {
        return blogpostRepository.findAll(pageable);
    }

    public void deleteBlogpost(int id, String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Blogpost blogpost = blogpostRepository.findById(id)
                .orElseThrow(() -> new BlogpostNotFoundException("Blogpost not found"));

        boolean isModerator = UserUtils.isUserAdmin();
        boolean isAuthor = blogpost.getUser().equals(user);
        if (isModerator || isAuthor) {
            blogpostRepository.deleteById(id);
            return;
        }
        throw new ForbiddenException("You are not allowed to delete this blogpost");
    }

    public Page<Blogpost> getAllBlogpostsByUserId(Integer userId, Pageable pageable) {
        return blogpostRepository.findAllByUserId(userId, pageable);
    }

    public boolean heartBlogpost(int id, String name) {
        User user = userService.getUserByUsername(name)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Blogpost blogpost = blogpostRepository.findById(id)
                .orElseThrow(() -> new BlogpostNotFoundException("Blogpost not found"));

        boolean hearted = toggleHeartStatus(blogpost, user);
        blogpostRepository.save(blogpost);

        return hearted;
    }

    public boolean getHeartBlogpost(int id, String name) {
        User user = userService.getUserByUsername(name)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Blogpost blogpost = blogpostRepository.findById(id)
                .orElseThrow(() -> new BlogpostNotFoundException("Blogpost not found"));

        return isBlogpostHeartedByUser(blogpost, user);
    }

    private boolean toggleHeartStatus(Blogpost blogpost, User user) {
        boolean wasHearted = blogpost.getHeartedByUsers().contains(user);
        if (wasHearted) {
            blogpost.getHeartedByUsers().remove(user);
        } else {
            blogpost.getHeartedByUsers().add(user);
        }
        return !wasHearted;
    }

    private boolean isBlogpostHeartedByUser(Blogpost blogpost, User user) {
        return blogpost.getHeartedByUsers().contains(user);
    }
}

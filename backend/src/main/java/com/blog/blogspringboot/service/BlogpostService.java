package com.blog.blogspringboot.service;

import com.blog.blogspringboot.dto.BlogpostRequestDTO;
import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.repository.BlogpostRepository;
import com.blog.blogspringboot.model.HeartBlogpostResponse;
import com.blog.blogspringboot.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

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

    public HttpStatus deleteBlogpost(int id, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Blogpost blogpost = getBlogpostById(id);
        if (blogpost == null) {
            return HttpStatus.NOT_FOUND;
        }
        boolean isModerator = UserUtils.isUserAdmin();
        boolean isAuthor = blogpost.getUser().equals(user);
        if (isModerator || isAuthor) {
            blogpostRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.FORBIDDEN;
    }

    public Page<Blogpost> getAllBlogpostsByUserId(Integer userId, Pageable pageable) {
        return blogpostRepository.findAllByUserId(userId, pageable);
    }

    public HeartBlogpostResponse heartBlogpost(int id, String name) {
        User user = userService.getUserByUsername(name);
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "User not found");
        }

        Blogpost blogpost = getBlogpostById(id);
        if (blogpost == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "Blogpost not found");
        }

        boolean wasHearted = toggleHeartStatus(blogpost, user);
        blogpostRepository.save(blogpost);

        return createSuccessResponse(!wasHearted);
    }

    public HeartBlogpostResponse getHeartBlogpost(int id, String name) {
        User user = userService.getUserByUsername(name);
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "User not found");
        }

        Blogpost blogpost = getBlogpostById(id);
        if (blogpost == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "Blogpost not found");
        }

        boolean hearted = isBlogpostHeartedByUser(blogpost, user);
        return createSuccessResponse(hearted);
    }

    private HeartBlogpostResponse createErrorResponse(HttpStatus status, String message) {
        return HeartBlogpostResponse.builder()
                .status(status)
                .success(false)
                .message(message)
                .hearted(false)
                .build();
    }

    private HeartBlogpostResponse createSuccessResponse(boolean hearted) {
        return HeartBlogpostResponse.builder()
                .status(HttpStatus.OK)
                .success(true)
                .hearted(hearted)
                .build();
    }

    private boolean toggleHeartStatus(Blogpost blogpost, User user) {
        boolean wasHearted = blogpost.getHeartedByUsers().contains(user);
        if (wasHearted) {
            blogpost.getHeartedByUsers().remove(user);
        } else {
            blogpost.getHeartedByUsers().add(user);
        }
        return wasHearted;
    }

    private boolean isBlogpostHeartedByUser(Blogpost blogpost, User user) {
        return blogpost.getHeartedByUsers().contains(user);
    }
}

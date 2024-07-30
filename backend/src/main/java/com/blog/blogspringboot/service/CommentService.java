package com.blog.blogspringboot.service;

import com.blog.blogspringboot.dto.CommentRequestDTO;
import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.Comment;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.exceptions.BlogpostNotFoundException;
import com.blog.blogspringboot.exceptions.CommentNotFoundException;
import com.blog.blogspringboot.exceptions.ForbiddenException;
import com.blog.blogspringboot.exceptions.UserNotFoundException;
import com.blog.blogspringboot.repository.CommentRepository;
import com.blog.blogspringboot.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final BlogpostService blogpostService;

    public Comment createComment(CommentRequestDTO commentRequestDTO, String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Blogpost blogpost = blogpostService.getBlogpostById(commentRequestDTO.getBlogpostId())
                .orElseThrow(() -> new BlogpostNotFoundException("Blogpost not found"));

        Comment comment = Comment.builder()
                .user(user)
                .blogpost(blogpost)
                .content(commentRequestDTO.getContent())
                .createdAt(new Date())
                .build();

        return commentRepository.save(comment);
    }

    public Comment getCommentById(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public void deleteComment(int id, Principal principal) {
        User user = userService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        boolean isModerator = UserUtils.isUserModerator();
        boolean isAuthor = comment.getUser().equals(user);
        if (isModerator || isAuthor) {
            commentRepository.deleteById(id);
            return;
        }
        throw new ForbiddenException("You are not allowed to delete this comment");
    }

    public Page<Comment> getAllCommentsByUserId(Integer userId, Pageable pageable) {
        return commentRepository.findAllByUserId(userId, pageable);
    }

    public Page<Comment> getAllCommentsByBlogpostId(Integer blogpostId, Pageable pageable) {
        return commentRepository.findAllByBlogpostId(blogpostId, pageable);
    }

    public Page<Comment> getAllCommentsByUserIdAndBlogpostId(Integer userId, Integer blogpostId, Pageable pageable) {
        return commentRepository.findByUserIdAndBlogpostId(userId, blogpostId, pageable);
    }

    public boolean heartComment(int id, String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        boolean wasHearted = toggleHeartStatus(comment, user);
        commentRepository.save(comment);

        return !wasHearted;
    }

    public boolean getHeartComment(int id, String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        return comment.getHeartedByUsers().contains(user);
    }

    private boolean toggleHeartStatus(Comment comment, User user) {
        boolean wasHearted = comment.getHeartedByUsers().contains(user);
        if (wasHearted) {
            comment.getHeartedByUsers().remove(user);
        } else {
            comment.getHeartedByUsers().add(user);
        }
        return wasHearted;
    }
    
}
package com.blog.blogspringboot.service;

import com.blog.blogspringboot.dto.CommentRequestDTO;
import com.blog.blogspringboot.entity.Comment;
import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final BlogpostService blogpostService;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment createComment(CommentRequestDTO commentRequestDTO, String username) {
        Comment comment = Comment.builder()
                .user(userService.getUserByUsername(username))
                .blogpost(blogpostService.getBlogpostById(commentRequestDTO.getBlogpostId()))
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

    public HttpStatus deleteComment(int id, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Comment comment = getCommentById(id);
        if (comment == null) {
            return HttpStatus.NOT_FOUND;
        }
        boolean isModerator = UserUtils.isUserModerator();
        boolean isAuthor = comment.getUser().equals(user);
        if (isModerator || isAuthor) {
            commentRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.FORBIDDEN;
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
}

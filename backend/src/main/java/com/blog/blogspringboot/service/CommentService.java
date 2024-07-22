package com.blog.blogspringboot.service;

import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.Comment;
import com.blog.blogspringboot.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getCommentById(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public void deleteComment(int id) {
        commentRepository.deleteById(id);
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

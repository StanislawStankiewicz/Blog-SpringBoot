package com.blog.blogspringboot.repository;

import com.blog.blogspringboot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}

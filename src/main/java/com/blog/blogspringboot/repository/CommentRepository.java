package com.blog.blogspringboot.repository;

import com.blog.blogspringboot.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findAllByUserId(Integer userId, Pageable pageable);

    Page<Comment> findAllByBlogpostId(Integer blogpostId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId AND c.blogpost.id = :blogpostId")
    Page<Comment> findByUserIdAndBlogpostId(@Param("userId") Integer userId, @Param("blogpostId") Integer blogpostId, Pageable pageable);
}

package com.blog.blogspringboot.repository;

import com.blog.blogspringboot.entity.Blogpost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogpostRepository extends JpaRepository<Blogpost, Integer> {
    Page<Blogpost> findAllByUserId(Integer userId, Pageable pageable);
}

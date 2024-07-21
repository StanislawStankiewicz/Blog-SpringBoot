package com.blog.blogspringboot.repository;

import com.blog.blogspringboot.entity.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogpostRepository extends JpaRepository<Blogpost, Integer> {
}

package com.blog.blogspringboot.repository;

import com.blog.blogspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

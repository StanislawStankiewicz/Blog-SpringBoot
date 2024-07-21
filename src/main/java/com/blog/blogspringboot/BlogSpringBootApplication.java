package com.blog.blogspringboot;

import com.blog.blogspringboot.entity.User;
import com.blog.blogspringboot.service.BlogpostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class BlogSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSpringBootApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BlogpostService blogpostService) {
        return args -> createBlogpost(blogpostService);
    }

    private void createBlogpost(BlogpostService blogpostService) {
        System.out.println(blogpostService.getBlogpostById(1));
    }
}

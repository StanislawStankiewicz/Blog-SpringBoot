package com.blog.blogspringboot;

import com.blog.blogspringboot.dao.BlogsiteDAO;
import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.User;
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
    public CommandLineRunner commandLineRunner(BlogsiteDAO blogsiteDAO) {
        return args -> createBlogpost(blogsiteDAO);
    }

    private void createBlogpost(BlogsiteDAO blogsiteDAO) {
        // Create a blogpost
        User user = blogsiteDAO.getUser(1);

        blogsiteDAO.createBlogpost(new Blogpost(user, "title", "content", 0, new Date(), null));
    }
}

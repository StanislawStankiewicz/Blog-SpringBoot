package com.blog.blogspringboot.dao;

import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.User;

public interface BlogsiteDAO {
    User getUser(int id);

    void createBlogpost(Blogpost blogpost);

}

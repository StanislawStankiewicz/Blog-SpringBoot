package com.blog.blogspringboot.dao;

import com.blog.blogspringboot.entity.Blogpost;
import com.blog.blogspringboot.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BlogsiteDAOImpl implements BlogsiteDAO {

    private static final Logger logger = LoggerFactory.getLogger(BlogsiteDAOImpl.class);
    private EntityManager em;

    @Autowired
    public BlogsiteDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getUser(int id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public void createBlogpost(Blogpost blogpost) {
        em.persist(blogpost);
        logger.info("Blogpost created: {}", blogpost);
    }
}

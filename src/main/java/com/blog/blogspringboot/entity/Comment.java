package com.blog.blogspringboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "blogpost_id", nullable = false)
    private Blogpost blogpost;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String content;

    private int hearts;

    public Comment() {
    }

    public Comment(User user, Blogpost blogpost, String content, int hearts) {
        this.user = user;
        this.blogpost = blogpost;
        this.content = content;
        this.hearts = hearts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blogpost getBlogpost() {
        return blogpost;
    }

    public void setBlogpost(Blogpost blogpost) {
        this.blogpost = blogpost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
//                ", user=" + user +
                ", blogpost=" + blogpost +
                ", content='" + content + '\'' +
                ", hearts=" + hearts +
                '}';
    }
}

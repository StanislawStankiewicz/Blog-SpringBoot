package com.blog.blogspringboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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
    @JsonBackReference
    private Blogpost blogpost;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String content;

    private int hearts;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Comment() {}

    public Comment(User user, Blogpost blogpost, String content, int hearts) {
        this.user = user;
        this.blogpost = blogpost;
        this.content = content;
        this.hearts = hearts;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", hearts=" + hearts +
                ", createdAt=" + createdAt +
                '}';
    }
}

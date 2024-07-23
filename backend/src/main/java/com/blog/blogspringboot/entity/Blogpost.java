package com.blog.blogspringboot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "blogposts")
public class Blogpost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private int hearts;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "blogpost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    public Blogpost() {}

    public Blogpost(User user, String title, String content, int hearts, Date createdAt, Set<Comment> comments) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.hearts = hearts;
        this.createdAt = createdAt;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blogpost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", hearts=" + hearts +
                ", createdAt=" + createdAt +
                '}';
    }
}

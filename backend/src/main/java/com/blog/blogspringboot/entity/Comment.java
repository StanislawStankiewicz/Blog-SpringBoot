package com.blog.blogspringboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "comment_hearts",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> heartedByUsers;

    @Transient
    private int hearts;

    public int getHearts() {
        return heartedByUsers != null ? heartedByUsers.size() : 0;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", hearts=" + getHearts() +
                ", createdAt=" + createdAt +
                '}';
    }
}

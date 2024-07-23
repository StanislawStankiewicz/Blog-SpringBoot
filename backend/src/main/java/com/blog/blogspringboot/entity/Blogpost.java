package com.blog.blogspringboot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "blogposts")
@Builder
@AllArgsConstructor
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

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "blogpost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "blogpost_hearts",
            joinColumns = @JoinColumn(name = "blogpost_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> heartedByUsers;

    @Transient
    private int hearts;

    public Blogpost() {}

    public Blogpost(User user, String title, String content, Date createdAt) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getHearts() {
        return heartedByUsers != null ? heartedByUsers.size() : 0;
    }

    @Override
    public String toString() {
        return "Blogpost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", hearts=" + getHearts() +
                ", createdAt=" + createdAt +
                '}';
    }
}

package com.blog.blogspringboot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void restartDatabase() {
        String[] sqlStatements = {
                "DROP TABLE IF EXISTS comment_hearts;",
                "DROP TABLE IF EXISTS blogpost_hearts;",
                "DROP TABLE IF EXISTS Comments;",
                "DROP TABLE IF EXISTS Blogposts;",
                "DROP TABLE IF EXISTS user_roles;",
                "DROP TABLE IF EXISTS Roles;",
                "DROP TABLE IF EXISTS Users;",
                "CREATE TABLE Users (id INT NOT NULL AUTO_INCREMENT, username VARCHAR(45) NOT NULL, email VARCHAR(45) NOT NULL, password VARCHAR(255) NOT NULL, created_at DATETIME, PRIMARY KEY (id), UNIQUE INDEX username_UNIQUE (username ASC), UNIQUE INDEX email_UNIQUE (email ASC));",
                "CREATE TABLE Roles (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, PRIMARY KEY (id), UNIQUE INDEX name_UNIQUE (name ASC));",
                "CREATE TABLE user_roles (user_id INT NOT NULL, role_id INT NOT NULL, PRIMARY KEY (user_id, role_id), CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE NO ACTION, CONSTRAINT fk_user_roles_roles FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE CASCADE ON UPDATE NO ACTION);",
                "CREATE TABLE Blogposts (id INT NOT NULL AUTO_INCREMENT, user_id INT, title VARCHAR(45), content TEXT, created_at DATETIME, PRIMARY KEY (id), UNIQUE INDEX id_UNIQUE (id ASC), INDEX fk_Blogposts_Users_idx (user_id ASC), CONSTRAINT fk_Blogposts_Users FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE NO ACTION ON UPDATE NO ACTION);",
                "CREATE TABLE Comments (id INT NOT NULL AUTO_INCREMENT, user_id INT, blogpost_id INT, content TINYTEXT, created_at DATETIME, PRIMARY KEY (id), UNIQUE INDEX id_UNIQUE (id ASC), INDEX fk_Comments_Blogposts1_idx (blogpost_id ASC), INDEX fk_Comments_Users1_idx (user_id ASC), CONSTRAINT fk_Comments_Blogposts1 FOREIGN KEY (blogpost_id) REFERENCES Blogposts (id) ON DELETE NO ACTION ON UPDATE NO ACTION, CONSTRAINT fk_Comments_Users1 FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE NO ACTION ON UPDATE NO ACTION);",
                "CREATE TABLE blogpost_hearts (blogpost_id INT NOT NULL, user_id INT NOT NULL, PRIMARY KEY (blogpost_id, user_id), CONSTRAINT fk_blogpost_hearts_blogposts FOREIGN KEY (blogpost_id) REFERENCES Blogposts (id) ON DELETE CASCADE ON UPDATE NO ACTION, CONSTRAINT fk_blogpost_hearts_users FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE NO ACTION);",
                "CREATE TABLE comment_hearts (comment_id INT NOT NULL, user_id INT NOT NULL, PRIMARY KEY (comment_id, user_id), CONSTRAINT fk_comment_hearts_comments FOREIGN KEY (comment_id) REFERENCES Comments (id) ON DELETE CASCADE ON UPDATE NO ACTION, CONSTRAINT fk_comment_hearts_users FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE NO ACTION);",
                "INSERT INTO Users (username, email, password, created_at) VALUES ('john_doe', 'john@example.com', '$2a$12$WFGAz6dXTUjGs.0dGHZL9udHj3wUSIILtFYsUQ9vH1oLdAjP4xPeq', NOW()), ('jane_smith', 'jane@example.com', '$2a$12$WFGAz6dXTUjGs.0dGHZL9udHj3wUSIILtFYsUQ9vH1oLdAjP4xPeq', NOW()), ('alice_jones', 'alice@example.com', '$2a$12$WFGAz6dXTUjGs.0dGHZL9udHj3wUSIILtFYsUQ9vH1oLdAjP4xPeq', NOW());",
                "INSERT INTO Roles (name) VALUES ('ROLE_USER'), ('ROLE_MODERATOR'), ('ROLE_ADMIN');",
                "INSERT INTO user_roles (user_id, role_id) VALUES (1, 1), (2, 1), (2, 2), (3, 1), (3, 2), (3, 3);",
                "INSERT INTO Blogposts (user_id, title, content, created_at) VALUES (1, 'First Post', 'This is the content of the first post.', NOW()), (2, 'Second Post', 'This is the content of the second post.', NOW()), (3, 'Third Post', 'This is the content of the third post.', NOW());",
                "INSERT INTO Comments (user_id, blogpost_id, content, created_at) VALUES (1, 1, 'Great post!', NOW()), (2, 1, 'Thanks for sharing!', NOW()), (3, 2, 'Very informative.', NOW()), (1, 3, 'I love this!', NOW()), (2, 3, 'Keep it up!', NOW());",
                "INSERT INTO blogpost_hearts (blogpost_id, user_id) VALUES (1, 1), (1, 2), (2, 3), (3, 1), (3, 2), (3, 3);",
                "INSERT INTO comment_hearts (comment_id, user_id) VALUES (1, 1), (1, 2), (2, 3), (3, 1), (4, 2), (4, 3), (5, 1);"
        };

        for (String sql : sqlStatements) {
            jdbcTemplate.execute(sql);
        }
    }
}

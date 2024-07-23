-- Drop tables if they exist
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Blogposts;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS Users;

-- Create the Users table
CREATE TABLE Users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME,
    PRIMARY KEY (id),
    UNIQUE INDEX username_UNIQUE (username ASC),
    UNIQUE INDEX email_UNIQUE (email ASC)
);

-- Create the Roles table
CREATE TABLE Roles (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX name_UNIQUE (name ASC)
);

-- Create the user_roles table for many-to-many relationship
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_users
        FOREIGN KEY (user_id)
        REFERENCES Users (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    CONSTRAINT fk_user_roles_roles
        FOREIGN KEY (role_id)
        REFERENCES Roles (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

-- Create the Blogposts table
CREATE TABLE Blogposts (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT,
    title VARCHAR(45),
    content TEXT,
    hearts INT,
    created_at DATETIME,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC),
    INDEX fk_Blogposts_Users_idx (user_id ASC),
    CONSTRAINT fk_Blogposts_Users
        FOREIGN KEY (user_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Create the Comments table
CREATE TABLE Comments (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT,
    blogpost_id INT,
    content TINYTEXT,
    hearts INT,
    created_at DATETIME,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC),
    INDEX fk_Comments_Blogposts1_idx (blogpost_id ASC),
    INDEX fk_Comments_Users1_idx (user_id ASC),
    CONSTRAINT fk_Comments_Blogposts1
        FOREIGN KEY (blogpost_id)
        REFERENCES Blogposts (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT fk_Comments_Users1
        FOREIGN KEY (user_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Drop tables if they exist
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Blogposts;
DROP TABLE IF EXISTS Users;

-- Create the Users table
CREATE TABLE Users (
                       id INT NOT NULL AUTO_INCREMENT,
                       username VARCHAR(45),
                       email VARCHAR(45),
                       created_at DATETIME,
                       PRIMARY KEY (id),
                       UNIQUE INDEX id_UNIQUE (id ASC)
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

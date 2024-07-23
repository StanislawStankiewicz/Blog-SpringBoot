-- Insert data into Users table
INSERT INTO Users (username, email, password, created_at) VALUES
    ('john_doe', 'john@example.com', '123', NOW()),
    ('jane_smith', 'jane@example.com', '123', NOW()),
    ('alice_jones', 'alice@example.com', '123', NOW());

-- Insert data into Roles table
INSERT INTO Roles (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_EDITOR'),
    ('ROLE_USER');

-- Insert data into user_roles table
INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 1),
    (3, 2),
    (3, 3);

-- Insert data into Blogposts table
INSERT INTO Blogposts (user_id, title, content, created_at) VALUES
    (1, 'First Post', 'This is the content of the first post.', NOW()),
    (2, 'Second Post', 'This is the content of the second post.', NOW()),
    (3, 'Third Post', 'This is the content of the third post.', NOW());

-- Insert data into Comments table
INSERT INTO Comments (user_id, blogpost_id, content, created_at) VALUES
    (1, 1, 'Great post!', NOW()),
    (2, 1, 'Thanks for sharing!', NOW()),
    (3, 2, 'Very informative.', NOW()),
    (1, 3, 'I love this!', NOW()),
    (2, 3, 'Keep it up!', NOW());

-- Insert data into blogpost_hearts table
INSERT INTO blogpost_hearts (blogpost_id, user_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 1),
    (3, 2),
    (3, 3);

-- Insert data into comment_hearts table
INSERT INTO comment_hearts (comment_id, user_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 1),
    (4, 2),
    (4, 3),
    (5, 1);
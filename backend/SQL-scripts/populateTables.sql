-- Insert data into Users table
INSERT INTO Users (username, email) VALUES
                                        ('john_doe', 'john@example.com'),
                                        ('jane_smith', 'jane@example.com'),
                                        ('alice_jones', 'alice@example.com');

-- Insert data into Blogposts table
INSERT INTO Blogposts (user_id, title, content, hearts, created_at) VALUES
                                                            (1, 'First Post', 'This is the content of the first post.', 10, NOW()),
                                                            (2, 'Second Post', 'This is the content of the second post.', 20, NOW()),
                                                            (3, 'Third Post', 'This is the content of the third post.', 30, NOW());

-- Insert data into Comments table
INSERT INTO Comments (user_id, blogpost_id, content, hearts) VALUES
                                                         (1, 1, 'Great post!', 2),
                                                         (2, 1, 'Thanks for sharing!', 4),
                                                         (3, 2, 'Very informative.', 5),
                                                         (1, 3, 'I love this!', 12),
                                                         (2, 3, 'Keep it up!', 0);
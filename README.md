# Blog App
Although this idea may seem unoriginal, I still decided to do it because of the learning value it provides. I believe it's important to point out - **I'm not using any tutorial for this** (except for security, this one was hard).

## Tech stack
|Backend|Frontend|Software|
|:-------------------------------------------:|:-------------------------------------------:|:-------------------------------------------:|
|Java<br>Spring Boot<br>MySQL|React<br>Typescript<br>Bootstrap<br>Vite|IntelliJ<br>Postman|

## Future plans
Right now the project doesn't provide much functionality. For future updates I plan to add:
* API documentation in swagger
* refactor server API logic to use well structured architecture
* editing and deleting UI functionality
* dockerization
* testing, both frontend and backend
* quick start section


## Database Schema
#### Tables and Relationships

1. **Users**:
   - Stores user information such as username, email, and password.
   - Establishes a relationship with blog posts and comments created by the user.
   - Associates users with roles through the `user_roles` table.

2. **Blogposts**:
   - Contains the content and metadata for each blog post.
   - Links each post to the user who created it.
   - Allows users to express their appreciation through `blogpost_hearts`.

3. **Comments**:
   - Holds comments made on blog posts.
   - Associates each comment with a specific user and blog post.
   - Enables users to like comments through `comment_hearts`.

4. **Roles**:
   - Defines various roles that users can have, such as admin or editor.
   - Connected to users via the `user_roles` table to manage permissions and access control.

5. **User Roles**:
   - Manages the many-to-many relationship between users and roles.
   - Assigns roles to users, facilitating role-based access control.

6. **Blogpost Hearts**:
   - Tracks user likes on blog posts.
   - Associates each like with a specific user and blog post.

7. **Comment Hearts**:
   - Tracks user likes on comments.
   - Associates each like with a specific user and comment.
-----
![image](https://github.com/user-attachments/assets/97928e0a-b3e6-4f58-ac04-b150c380ff45)

## Testing
For manual API testing Postman was used.

![image](https://github.com/user-attachments/assets/095bc4b7-9578-4891-8e97-c551f47af5b8)

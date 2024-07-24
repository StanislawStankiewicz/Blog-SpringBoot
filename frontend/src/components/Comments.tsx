import { useState } from "react";
import { Comment as CommentT } from "../types/Comment.type";
import { formatDate } from "../utils/date";
import { User } from "../types/User.type";

export default function Comments({
  comments,
  username,
  token,
  blogpostId,
}: {
  comments: CommentT[] | undefined;
  username: string | null;
  token: string | null;
  blogpostId: number;
}) {
  const [newComment, setNewComment] = useState("");
  const [commentList, setCommentList] = useState(
    comments ? comments : ([] as CommentT[])
  );

  const handleCommentChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewComment(e.target.value);
  };

  const handleCommentSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (newComment.trim() === "") return;

    if (!username) {
      console.error("You are not logged in.");
      return;
    }

    // fake visual feedback
    const newCommentObject: CommentT = {
      id: commentList.length + 1,
      user: new User(0, username),
      content: newComment,
      createdAt: new Date().toISOString(),
      hearts: 0,
    };

    setCommentList([...commentList, newCommentObject]);
    setNewComment("");

    // POST request
    const commentRequest = {
      blogpostId,
      content: newComment,
    };

    fetch(`/api/comments`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(commentRequest),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  return (
    <>
      <div className="card-header px-5">Comments</div>
      <ul className="list-group list-group-flush">
        {commentList.map((comment, index) => (
          <li key={index} className="list-group-item px-5">
            <div className="d-flex justify-content-between align-items-center">
              <small className="text-muted">{comment.user.username}</small>
              <small className="text-muted">
                {formatDate(new Date(comment.createdAt))}
              </small>
            </div>
            <p className="m-0">{comment.content}</p>
            <div className="d-flex justify-content-end">
              <p className="card-text mb-0">
                <small className="text-muted">Hearts: {comment.hearts}</small>
              </p>
            </div>
          </li>
        ))}
      </ul>
      <form onSubmit={handleCommentSubmit} className="px-5 my-3">
        <div className="input-group">
          <input
            type="text"
            className="form-control"
            placeholder="Add a comment..."
            value={newComment}
            onChange={handleCommentChange}
            disabled={!username}
          />
          <button
            className="btn btn-primary"
            type="submit"
            disabled={!username}
          >
            Submit
          </button>
        </div>
      </form>
    </>
  );
}

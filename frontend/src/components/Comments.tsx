import { useState } from "react";
import { Comment as CommentT } from "../types/Comment.type";
import { formatDate } from "../utils/date";

export default function Comments({ comments }: { comments: CommentT[] }) {
  const [newComment, setNewComment] = useState("");
  const [commentList, setCommentList] = useState(comments);

  const handleCommentChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewComment(e.target.value);
  };

  const handleCommentSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (newComment.trim() === "") return;

    const newCommentObject: CommentT = {
      user: { username: "current_user" }, // Replace with the actual current user
      content: newComment,
      createdAt: new Date().toISOString(),
    };

    setCommentList([...commentList, newCommentObject]);
    setNewComment("");
  };

  return (
    <>
      <div className="card-header px-5">Comments</div>
      <ul className="list-group list-group-flush">
        {commentList.map((comment, index) => (
          <li key={index} className="list-group-item px-5">
            <div className="d-flex justify-content-between align-items-center">
              <small className="text-muted">{comment.user.username}</small>
              <small className="text-muted">{formatDate(new Date())}</small>
            </div>
            <p className="m-0">{comment.content}</p>
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
          />
          <button className="btn btn-primary" type="submit">
            Submit
          </button>
        </div>
      </form>
    </>
  );
}

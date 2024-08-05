import type { Blogpost } from "../types/Blogpost.type";
import { formatDate } from "../utils/date";
import Comments from "./Comments";
import HeartButton from "./HeartButton";

export default function Blogpost({
  post,
  username,
  id,
}: {
  post: Blogpost;
  username: string | null;
  id: number;
}) {
  return (
    <div key={post.id} className="card mt-3">
      <div className="card-body">
        <div className="d-flex justify-content-between align-items-center mb-2">
          <div>
            <h5 className="card-title d-inline me-2">{post.title}</h5>
            <small className="card-subtitle mb-2 text-muted d-inline">
              by {post.user.username}
            </small>
          </div>
          <small className="text-muted">
            {formatDate(new Date(post.createdAt))}
          </small>
        </div>
        <p className="card-text mb-0">{post.content}</p>
        <div className="d-flex justify-content-end">
          <HeartButton
            initialCount={post.hearts}
            path="blogposts"
            id={id}
            locked={!username}
          />
        </div>
      </div>
      <div className="card-footer p-0 overflow-hidden">
        <Comments
          comments={post.comments}
          username={username}
          blogpostId={id}
        />
      </div>
    </div>
  );
}

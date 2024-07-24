import Blogpost from "./Blogpost";
import { Blogpost as BlogpostT } from "../types/Blogpost.type";

const Blogposts = ({
  posts,
  username,
  token,
}: {
  posts: BlogpostT[];
  username: string | null;
  token: string | null;
}) => {
  return (
    <div className="blogposts">
      {posts.map((post) => (
        <Blogpost
          key={post.id}
          id={post.id}
          post={post}
          username={username}
          token={token}
        />
      ))}
    </div>
  );
};

export default Blogposts;

import Blogpost from "./Blogpost";
import { Blogpost as BlogpostT } from "../types/Blogpost.type";

const Blogposts = ({
  posts,
  username,
}: {
  posts: BlogpostT[];
  username: string | null;
}) => {
  return (
    <div className="blogposts">
      {posts.map((post) => (
        <Blogpost key={post.id} id={post.id} post={post} username={username} />
      ))}
    </div>
  );
};

export default Blogposts;

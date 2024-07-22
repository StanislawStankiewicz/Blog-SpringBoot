import React from "react";
import Blogpost from "./components/Blogpost";
import type { Blogpost as BlogpostT } from "./types/Blogpost.type";

function Blogsite() {
  const [posts, setPosts] = React.useState<BlogpostT[]>([]);

  React.useEffect(() => {
    async function fetchBlogposts(): Promise<BlogpostT[]> {
      const response = await fetch("/api/blogposts").then((res) => res.json());
      const blogposts: BlogpostT[] = response.content;
      console.log(blogposts);
      setPosts(blogposts);
      return blogposts;
    }
    fetchBlogposts();
  }, []);

  return (
    <div className="container my-3">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <h1 className="text-center">Blogsite</h1>
          {posts.map((post) => (
            <Blogpost key={post.id} post={post} />
          ))}
        </div>
      </div>
    </div>
  );
}

export default Blogsite;

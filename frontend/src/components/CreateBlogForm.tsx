import { useState } from "react";
import { Blogpost } from "../types/Blogpost.type";

function CreateBlogForm({
  token,
  onPostCreated,
}: {
  token: string | null;
  onPostCreated: (post: Blogpost) => void;
}) {
  const [newTitle, setNewTitle] = useState("");
  const [newContent, setNewContent] = useState("");

  async function handleCreatePost(e: React.FormEvent) {
    e.preventDefault();
    if (!newTitle.trim() || !newContent.trim()) return;

    const postRequest = {
      title: newTitle,
      content: newContent,
    };

    const response = await fetch("/api/blogposts", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(postRequest),
    }).then((res) => res.json());

    if (response.id) {
      onPostCreated(response);
      setNewTitle("");
      setNewContent("");
    }
  }

  return (
    <form onSubmit={handleCreatePost} className="mb-3">
      <div className="mb-3">
        <label className="form-label">Title</label>
        <input
          type="text"
          className="form-control"
          value={newTitle}
          onChange={(e) => setNewTitle(e.target.value)}
          required
        />
      </div>
      <div className="mb-3">
        <label className="form-label">Content</label>
        <textarea
          className="form-control"
          value={newContent}
          onChange={(e) => setNewContent(e.target.value)}
          required
        ></textarea>
      </div>
      <button type="submit" className="btn btn-primary">
        Post
      </button>
    </form>
  );
}

export default CreateBlogForm;

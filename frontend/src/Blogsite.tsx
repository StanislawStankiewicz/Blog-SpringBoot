import { useState, useEffect } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import Login from "./components/Login";
import Blogposts from "./components/Blogposts";
import { Blogpost as BlogpostT } from "./types/Blogpost.type";
import LoginButton from "./components/LoginButton";
import CreateBlogForm from "./components/CreateBlogForm";

function Blogsite() {
  const [username, setUsername] = useState<string | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [posts, setPosts] = useState<BlogpostT[]>([]);
  const [showCreateForm, setShowCreateForm] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    console.log("token: " + token);
  }, [token]);

  useEffect(() => {
    async function fetchBlogposts(): Promise<BlogpostT[]> {
      const response = await fetch("/api/blogposts").then((res) => res.json());
      const blogposts: BlogpostT[] = response.content;
      setPosts(blogposts);
      return blogposts;
    }
    fetchBlogposts();
  }, []);

  async function handleLogin(username: string, password: string) {
    const loginRequest = {
      username,
      password,
    };

    const response = await fetch("/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(loginRequest),
    }).then((res) => res.json());
    if (response.accessToken) {
      setUsername(username);
      setToken(response.accessToken);
      navigate("/");
    }
  }

  function handleLogout() {
    setUsername(null);
    setToken(null);
    navigate("/");
  }

  return (
    <div className="container my-3">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div
            style={{
              display: "flex",
              flexDirection: "row-reverse",
              alignItems: "center",
            }}
          >
            <LoginButton username={username} onLogout={handleLogout} />
            <h1 className="text-center" style={{ flexGrow: 1 }}>
              Blogsite
            </h1>
            <div style={{ width: "80px", visibility: "hidden" }}>Spacer</div>
          </div>
          <div className="text-center my-3">
            <button
              className="btn btn-success"
              onClick={() => setShowCreateForm(!showCreateForm)}
              disabled={!username}
            >
              {showCreateForm ? "Cancel" : "Create a Blog"}
            </button>
          </div>
          {showCreateForm && (
            <CreateBlogForm
              token={token}
              onPostCreated={(post: BlogpostT) => setPosts([post, ...posts])}
            />
          )}
          <Routes>
            <Route path="/login" element={<Login onLogin={handleLogin} />} />
            <Route
              path="/"
              element={
                <Blogposts posts={posts} username={username} token={token} />
              }
            />
          </Routes>
        </div>
      </div>
    </div>
  );
}

export default Blogsite;

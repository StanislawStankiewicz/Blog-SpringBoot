import { useState, useEffect } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import Login from "./components/Login";
import Signup from "./components/Signup";
import Blogposts from "./components/Blogposts";
import { Blogpost as BlogpostT } from "./types/Blogpost.type";
import LoginButton from "./components/LoginButton";
import CreateBlogForm from "./components/CreateBlogForm";
import "./Blogsite.css";

function Blogsite() {
  const [username, setUsername] = useState<string | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [posts, setPosts] = useState<BlogpostT[]>([]);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [slideIn, setSlideIn] = useState(false);

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

  function toggleCreateForm() {
    if (showCreateForm) {
      setSlideIn(false);
      setTimeout(() => setShowCreateForm(false), 500); // match the transition duration
    } else {
      setShowCreateForm(true);
      setTimeout(() => setSlideIn(true), 0); // ensure it's applied after render
    }
  }

  return (
    <div className="container my-3">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div
            style={{
              position: "relative",
              display: "flex",
              justifyContent: "space-between",
              flexDirection: "row-reverse",
              alignItems: "center",
            }}
          >
            <div style={{ display: "flex", gap: "10px" }}>
              <LoginButton username={username} onLogout={handleLogout} />
              {!username && (
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => navigate("/signup")}
                >
                  Sign Up
                </button>
              )}
            </div>
            <h1
              className="text-center"
              style={{
                position: "absolute",
                left: "50%",
                transform: "translateX(-50%)",
              }}
            >
              Blogsite
            </h1>
            <div style={{ width: "80px", visibility: "hidden" }}>Spacer</div>
          </div>

          <div className="text-center my-3">
            <button
              className="btn btn-success"
              onClick={toggleCreateForm}
              disabled={!username}
            >
              {showCreateForm ? "Cancel" : "Create a Blog"}
            </button>
          </div>
          {showCreateForm && (
            <div
              className={slideIn ? "slide-enter-active" : "slide-exit-active"}
            >
              <CreateBlogForm
                token={token}
                onPostCreated={(post) => {
                  setPosts([...posts, post]);
                  toggleCreateForm();
                }}
              />
            </div>
          )}
          <Routes>
            <Route path="/login" element={<Login onLogin={handleLogin} />} />
            <Route path="/signup" element={<Signup />} />
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

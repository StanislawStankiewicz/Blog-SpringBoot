import { useState, useEffect } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import Login from "./components/Login";
import Signup from "./components/Signup";
import Blogposts from "./components/Blogposts";
import { Blogpost as BlogpostT } from "./types/Blogpost.type";
import LoginButton from "./components/LoginButton";
import CreateBlogForm from "./components/CreateBlogForm";
import RestartDatabaseButton from "./components/RestartDatabaseButton";
import "./Blogsite.css";
import InfoCard from "./components/InfoCard";

function Blogsite() {
  const [username, setUsername] = useState<string | null>(null);
  const [posts, setPosts] = useState<BlogpostT[]>([]);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [slideIn, setSlideIn] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await fetch("/auth/verify", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include", // Ensure cookies are included in the request
        });
        if (response.ok) {
          const data = await response.json();
          setUsername(data.username);
        } else {
          navigate("/login");
        }
      } catch (error) {
        console.error("Error verifying token:", error);
        navigate("/login");
      }
    };

    checkAuth();
  }, []);

  useEffect(() => {
    async function fetchBlogposts(): Promise<BlogpostT[]> {
      const response = await fetch("/api/blogposts").then((res) => res.json());
      const blogposts: BlogpostT[] = response.content;
      setPosts(blogposts);
      return blogposts;
    }
    fetchBlogposts();
  }, []);

  async function handleLogin(
    username: string,
    password: string
  ): Promise<{
    success: boolean;
    message: string;
    accessToken: string | null;
  }> {
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
      credentials: "include", // Ensure cookies are included in the request
    });

    const data = await response.json();
    if (response.ok) {
      setUsername(username);
      navigate("/");
      return { success: true, message: "", accessToken: data.accessToken };
    } else {
      return { success: false, message: data.message, accessToken: null };
    }
  }

  function handleLogout() {
    setUsername(null);
    document.cookie =
      "accessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
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
              Blogsite DEMO
            </h1>
            <div style={{ width: "80px", visibility: "hidden" }}>Spacer</div>
          </div>

          <Routes>
            <Route path="/login" element={<Login onLogin={handleLogin} />} />
            <Route path="/signup" element={<Signup />} />
            <Route
              path="/"
              element={
                <>
                  <div className="d-flex justify-content-center my-3">
                    <div style={{ width: "600px" }}>
                      <InfoCard
                        message="This is a demo version. Any user that successfuly logs in (which is not hard) can add content. If you feel like any of the content here is inappropriate or offensive, feel free to log in, and restart the database, using the button at the bottom of the page."
                        type="info"
                      />
                    </div>
                  </div>

                  <div className="text-center mb-3">
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
                      className={
                        slideIn ? "slide-enter-active" : "slide-exit-active"
                      }
                    >
                      <CreateBlogForm
                        onPostCreated={(post) => {
                          setPosts([...posts, post]);
                          toggleCreateForm();
                        }}
                      />
                    </div>
                  )}
                  <Blogposts posts={posts} username={username} />
                </>
              }
            />
          </Routes>
          <RestartDatabaseButton username={username} />
        </div>
      </div>
    </div>
  );
}

export default Blogsite;

import { useState } from "react";
import InfoCard from "./InfoCard";

function Signup() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  async function handleSignup(e: React.FormEvent) {
    e.preventDefault();

    if (password.length > 4) {
      setErrorMessage("Password must be no longer than 4 characters.");
      setPassword("");
      return;
    }
    const signupRequest = {
      username,
      password,
      email,
    };

    const response = await fetch("/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(signupRequest),
    }).then((res) => res.json());

    if (!response.success) {
      setErrorMessage(response.message);
    } else {
      setErrorMessage(null);
      alert("Signup successful! Please log in.");
    }
  }

  return (
    <div className="d-flex justify-content-center vh-100">
      <div className="signup-form" style={{ width: "500px" }}>
        <InfoCard
          message="This is a demo version. Because of that, the password length is limited to 4 characters. Do not use your real password since the connection is not secure."
          type="info"
        />
        <h2>Sign Up</h2>
        {errorMessage && <InfoCard message={errorMessage} type="error" />}
        <form onSubmit={handleSignup}>
          <div className="mb-3">
            <label className="form-label">Username</label>
            <input
              type="text"
              className="form-control"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Email</label>
            <input
              type="email"
              className="form-control"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary">
            Sign Up
          </button>
        </form>
      </div>
    </div>
  );
}

export default Signup;

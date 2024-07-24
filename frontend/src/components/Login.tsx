import React, { useState } from "react";
import InfoCard from "./InfoCard";

type Props = {
  onLogin: (
    username: string,
    password: string
  ) => Promise<{
    success: boolean;
    message: string;
    accessToken: string | null;
  }>;
};

const Login: React.FC<Props> = ({ onLogin }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (password.length > 4) {
      setErrorMessage("Invalid username or password.");
      setPassword("");
      return;
    }

    const response = await onLogin(username, password);
    if (!response.success) {
      setPassword("");
      setErrorMessage(response.message);
    } else {
      setErrorMessage(null);
    }
  };

  return (
    <div className="d-flex justify-content-center">
      <div className="login-form" style={{ width: "500px" }}>
        <InfoCard
          message="This is a demo version. Because of that, the password length is limited to 4 characters. Do not use your real password since the connection is not secure."
          type="info"
        />
        <div className=" d-flex justify-content-center align-items-center">
          <h2 className="mb-0">Login</h2>
        </div>
        {errorMessage && <InfoCard message={errorMessage} type="error" />}
        <form onSubmit={handleSubmit}>
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
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;

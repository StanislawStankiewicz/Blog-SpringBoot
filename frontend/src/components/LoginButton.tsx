import { useNavigate } from "react-router-dom";

export default function LoginButton({ username, onLogout }) {
  const navigate = useNavigate();

  return username ? (
    <button type="button" className="btn btn-primary" onClick={onLogout}>
      Log off
    </button>
  ) : (
    <button
      type="button"
      className="btn btn-primary"
      onClick={() => navigate("/login")}
    >
      Log in
    </button>
  );
}

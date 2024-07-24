import { useNavigate } from "react-router-dom";

export default function LoginButton({
  username,
  onLogout,
}: {
  username: string | null;
  onLogout: () => void;
}) {
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

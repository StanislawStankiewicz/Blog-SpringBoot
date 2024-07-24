import { useEffect, useState } from "react";
import heartFilled from "../assets/heart-filled.svg";
import heartEmpty from "../assets/heart-empty.svg";
import "./HeartButton.css"; // Make sure to import the CSS file where the .muted-icon class is defined

interface HeartButtonProps {
  initialCount: number;
  size?: number;
  token: string | null;
  path: "comments" | "blogposts";
  id: number;
  locked?: boolean;
}

const HeartButton: React.FC<HeartButtonProps> = ({
  initialCount,
  size = 20,
  token,
  path,
  id,
  locked = false,
}) => {
  const [count, setCount] = useState(initialCount);
  const [clicked, setClicked] = useState(false);

  useEffect(() => {
    if (locked) return;
    fetchIsHearted(token!, path, id)
      .then((res) => res.json())
      .then((response) => {
        if (response && response.isHearted !== clicked) {
          setClicked(response.isHearted);
        }
      });
  });

  function handleClick() {
    if (locked) return;
    setClicked(!clicked);
    setCount(clicked ? count - 1 : count + 1);
    fetchIsHearted(token!, path, id, "POST")
      .then((res) => res.json())
      .then(async (response) => {
        console.log(response);
        if (response.isHearted !== clicked) {
          setCount(response.isHearted ? count + 1 : count - 1);
          setClicked(response.isHearted);
        }
      });
  }

  return (
    <div
      onClick={handleClick}
      style={{ cursor: "pointer", display: "flex", alignItems: "center" }}
    >
      <img
        src={clicked ? heartFilled : heartEmpty}
        alt="Heart"
        width={size}
        height={size}
        className="muted-icon"
      />
      <span style={{ marginLeft: "8px" }} className="muted-text">
        {count}
      </span>
    </div>
  );
};

export default HeartButton;

function fetchIsHearted(
  token: string,
  path: string,
  id: number,
  method: "GET" | "POST" = "GET"
) {
  return fetch(`/api/${path}/${id}/heart`, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
}

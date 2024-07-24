import { useState } from "react";
import heartFilled from "../assets/heart-filled.svg";
import heartEmpty from "../assets/heart-empty.svg";
import "./HeartButton.css"; // Make sure to import the CSS file where the .muted-icon class is defined

interface HeartButtonProps {
  initialCount: number;
  size?: number;
}

const HeartButton: React.FC<HeartButtonProps> = ({
  initialCount,
  size = 20,
}) => {
  const [count, setCount] = useState(initialCount);
  const [clicked, setClicked] = useState(false);

  const handleClick = () => {
    setClicked(!clicked);
    setCount(clicked ? count - 1 : count + 1);
    console.log(`Heart ${clicked ? "removed" : "added"}`);
  };

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

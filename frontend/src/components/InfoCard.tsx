import React from "react";

type InfoCardProps = {
  message: string;
  type?: "success" | "error" | "info";
};

const InfoCard: React.FC<InfoCardProps> = ({ message, type = "info" }) => {
  let cardStyle = {
    padding: "1rem",
    margin: "1rem 0",
    border: "1px solid",
    borderRadius: "5px",
    backgroundColor: "",
    color: "",
  };

  switch (type) {
    case "error":
      cardStyle = {
        ...cardStyle,
        border: "1px solid #dc3545",
        backgroundColor: "#343a40",
        color: "#dc3545",
      };
      break;
    case "success":
      cardStyle = {
        ...cardStyle,
        border: "1px solid #28a745",
        backgroundColor: "#343a40",
        color: "#28a745",
      };
      break;
    case "info":
    default:
      cardStyle = {
        ...cardStyle,
        border: "1px solid #17a2b8",
        backgroundColor: "#343a40",
        color: "#17a2b8",
      };
      break;
  }

  return <div style={cardStyle}>{message}</div>;
};

export default InfoCard;

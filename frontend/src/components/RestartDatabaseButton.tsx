interface Props {
  token: string | null;
}

function RestartDatabaseButton({ token }: Props) {
  async function handleRestartDatabase() {
    const userConfirmed = window.confirm(
      "Are you sure you want to restart the database? This action cannot be undone."
    );

    if (userConfirmed && token) {
      const response = await fetch("/nuke/db", {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        alert("Database has been restarted successfully.");
        window.location.reload();
      } else {
        alert("Failed to restart the database.");
      }
    }
  }

  return (
    <div className="d-flex justify-content-center my-3">
      <button
        className="btn btn-danger"
        onClick={handleRestartDatabase}
        disabled={!token}
      >
        Restart Database
      </button>
    </div>
  );
}

export default RestartDatabaseButton;

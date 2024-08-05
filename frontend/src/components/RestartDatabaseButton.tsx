interface Props {
  username: string | null;
}

function RestartDatabaseButton({ username }: Props) {
  async function handleRestartDatabase() {
    const userConfirmed = window.confirm(
      "Are you sure you want to restart the database? This action cannot be undone."
    );

    if (userConfirmed) {
      const response = await fetch("/nuke/db", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
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
        disabled={!username}
      >
        Restart Database
      </button>
    </div>
  );
}

export default RestartDatabaseButton;

import React from "react";

const useError = () => {
  const [error, setError] = React.useState<{ error: boolean; message: string }>(
    {
      error: false,
      message: "",
    }
  );

  const Error = () => {
    return (
      <>
        {error.message.length > 0 && (
          <div
            className={`p-3 text-center rounded-lg text-white ${
              !error.error ? "bg-green-400" : "bg-red-400"
            }`}
          >
            {error.message}
          </div>
        )}
      </>
    );
  };

  return [error, setError, Error] as const;
};

export default useError;

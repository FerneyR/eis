import React from "react";
import Webcam from "react-webcam";
import { b64toBlob } from "../utils/toBlob";

const useCamera = () => {
  const [photo, setPhoto] = React.useState<string>("");
  const [blobPhoto, setBlobPhoto] = React.useState<Blob | null>(null);
  const [cameraPermissions, setCameraPersmissions] =
    React.useState<boolean>(false);

  const [stream, setStream] = React.useState<MediaStream | null>(null);

  const webcamRef = React.useRef<Webcam>(null);
  const capture = React.useCallback(() => {
    if (webcamRef.current === null) return;
    const imageSrc = webcamRef.current.getScreenshot();
    if (imageSrc) {
      setPhoto(imageSrc);
      setBlobPhoto(b64toBlob(imageSrc.split(",")[1], "image/jpeg"));
    }
  }, [webcamRef]);

  const resetWebCamState = () => {
    setPhoto("");
    setBlobPhoto(null);
  };

  React.useEffect(() => {
    const requestCameraPermission = async () => {
      navigator.mediaDevices
        .getUserMedia({ video: true })
        .then(function (stream) {
          setStream(stream);
          // Tienes permisos de cámara, el stream de la cámara se puede usar aquí
          setCameraPersmissions(true);
        })
        .catch(function (error) {
          // No tienes permisos de cámara o ocurrió un error
          console.error(
            "No tienes permisos de cámara o ocurrió un error",
            error
          );
          setCameraPersmissions(false);
        });
    };
    if (!stream) {
      requestCameraPermission();
    }

    const turnOffCamera = () => {
      if (stream) {
        const tracks = stream.getTracks();
        tracks.forEach((track) => {
          track.stop();
        });
      }
    };
    return () => {
      turnOffCamera();
    };
  }, [stream]);

  const Camera = () => {
    return (
      <>
        {cameraPermissions ? (
          <>
            {photo ? (
              <img src={photo} alt="photo" className="w-full object-cover" />
            ) : (
              <>
                <Webcam
                  mirrored={true}
                  screenshotFormat="image/jpeg"
                  ref={webcamRef}
                />
              </>
            )}
            <div className="flex gap-2 mt-2">
              <button
                type="button"
                className="text-center w-1/2 p-1 bg-indigo-600 rounded-md text-white hover:bg-indigo-400 
                     text-md transition-all ease-linear duration-100"
                onClick={capture}
                disabled={photo.length > 0}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="currentColor"
                  className="w-6 h-6 inline"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M6.827 6.175A2.31 2.31 0 015.186 7.23c-.38.054-.757.112-1.134.175C2.999 7.58 2.25 8.507 2.25 9.574V18a2.25 2.25 0 002.25 2.25h15A2.25 2.25 0 0021.75 18V9.574c0-1.067-.75-1.994-1.802-2.169a47.865 47.865 0 00-1.134-.175 2.31 2.31 0 01-1.64-1.055l-.822-1.316a2.192 2.192 0 00-1.736-1.039 48.774 48.774 0 00-5.232 0 2.192 2.192 0 00-1.736 1.039l-.821 1.316z"
                  />
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M16.5 12.75a4.5 4.5 0 11-9 0 4.5 4.5 0 019 0zM18.75 10.5h.008v.008h-.008V10.5z"
                  />
                </svg>
              </button>
              <button
                type="button"
                className="text-center w-1/2 p-1 bg-indigo-600 rounded-md text-white hover:bg-indigo-400 
                    text-md transition-all ease-linear duration-100"
                onClick={resetWebCamState}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="currentColor"
                  className="w-6 h-6 inline"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99"
                  />
                </svg>
              </button>
            </div>
          </>
        ) : (
          <p className="text-red-500">Camera permissions denied</p>
        )}
      </>
    );
  };

  return [blobPhoto, Camera, resetWebCamState] as const;
};

export default useCamera;

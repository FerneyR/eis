import { Tab } from "@headlessui/react";
import React from "react";
import { baseMinioUrl, baseRegisterUrl } from "../utils/api";
import useCamera from "../hooks/useCamera";
import useError from "../hooks/useError";

const FormEmployee = () => {
  const [selectedFile, setSelectedFile] = React.useState<File | null>(null);
  const [fileName, setSelectFileName] = React.useState<string>("");
  const [employee, setEmployee] = React.useState<{
    id: string;
    name: string;
    photo: string;
  }>({
    photo: "",
    id: "",
    name: "",
  });

  const [photoBlob, Camera, resetWebCamState] = useCamera();
  const [, setError, ErrorComponent] = useError();

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      setSelectedFile(event.target.files[0]);
      setSelectFileName(event.target.files[0].name);
    }
  };

  const handleChangeForm = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmployee({
      ...employee,
      [event.target.name]: event.target.value,
    });
  };

  const handleChangeTab = () => {
    setSelectedFile(null);
    resetWebCamState();
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await baseRegisterUrl.post("/employees", employee, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.status !== 201)
        throw new Error("Error al crear el empleado");
      const employeeId: number = response.data.employeeId;

      const formData = new FormData();

      if (photoBlob !== null) {
        formData.append(
          "file",
          photoBlob ? photoBlob : new Blob(),
          `${employeeId}.jpg`
        );
      } else {
        formData.append(
          "file",
          selectedFile ? selectedFile : new Blob(),
          employeeId + fileName.substring(fileName.length - 4)
        );
      }

      const responsePhoto = await baseMinioUrl.post(
        "/employees/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      if (responsePhoto.status !== 201)
        throw new Error("Error al subir la foto");

      setError({ error: false, message: "Employee created" });

      setTimeout(() => {
        window.location.reload();
      }, 1200);
    } catch (error) {
      console.log(error);
      setError({ error: true, message: "Cannot create employee" });
    }
  };

  const type = {
    "Upload File": [
      {
        html: (
          <div className="max-w-2xl mx-auto">
            <label
              className="block mb-2 text-sm font-medium text-gray-900 "
              htmlFor="file_input"
            >
              Upload file
            </label>
            <input
              className="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
              id="file_input"
              type="file"
              accept=".jpg"
              onChange={handleFileChange}
            />
          </div>
        ),
      },
    ],
    "Take Photo": [
      {
        html: (
          <div className="max-w-2xl mx-auto">
            <Camera />
          </div>
        ),
      },
    ],
  };

  const classNames = (...classes: string[]) => {
    return classes.filter(Boolean).join(" ");
  };

  return (
    <div>
      <ErrorComponent />
      <form onSubmit={handleSubmit}>
        <div className="flex flex-wrap -mx-3 mb-2">
          <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
            <label
              className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
              htmlFor="id"
            >
              Employee ID
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700 border  py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
              id="id"
              name="id"
              type="text"
              placeholder="10"
              onChange={handleChangeForm}
            />
          </div>
          <div className="w-full md:w-1/2 px-3">
            <label
              className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
              htmlFor="name"
            >
              Employee Name
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700 border rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white "
              id="name"
              name="name"
              type="text"
              placeholder="Doe"
              onChange={handleChangeForm}
            />
          </div>
        </div>
        {employee.name !== "" && employee.id !== "" && (
          <div className="w-full max-w-md px-2 py-3 sm:px-0">
            <Tab.Group onChange={() => handleChangeTab()}>
              <Tab.List className="flex space-x-1 rounded-xl bg-blue-900/20 p-1">
                {Object.keys(type).map((typeMethod) => (
                  <Tab
                    key={typeMethod}
                    className={({ selected }) =>
                      classNames(
                        "w-full rounded-lg py-2.5 text-sm font-medium leading-5 text-blue-700",
                        "ring-white ring-opacity-60 ring-offset-2  focus:outline-none focus:ring-2",
                        selected
                          ? "bg-white shadow"
                          : "text-blue-100 hover:bg-white/[0.12] hover:text-white"
                      )
                    }
                  >
                    {typeMethod}
                  </Tab>
                ))}
              </Tab.List>
              <Tab.Panels className="mt-2">
                {Object.values(type).map((typeValue, idx) => (
                  <Tab.Panel
                    key={idx}
                    className={classNames(
                      "rounded-xl bg-white p-3",
                      "ring-white ring-opacity-60 ring-offset-2  focus:outline-none focus:ring-2"
                    )}
                  >
                    {typeValue[0].html}
                  </Tab.Panel>
                ))}
              </Tab.Panels>
            </Tab.Group>
          </div>
        )}
        <button
          className="w-full bg-indigo-600 p-2 mb-3 text-white rounded hover:bg-indigo-500"
          disabled={
            (employee.name === "" && employee.id === "") ||
            (photoBlob === null && selectedFile === null)
          }
          type="submit"
        >
          Created Employee
        </button>
      </form>
    </div>
  );
};

export default FormEmployee;

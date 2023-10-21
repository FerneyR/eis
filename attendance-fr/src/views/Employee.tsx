import React from "react";
import { baseRegisterUrl } from "../utils/api";
import Tittle from "../components/Tittle";
import { IEmployee } from "../vite-env";
import FormEmployee from "../components/FormNewEmployee";
import useError from "../hooks/useError";
import useModal from "../hooks/useModal";

const Employee = () => {
  const [data, setData] = React.useState<IEmployee[]>([
    {
      employeeId: 0,
      id: "",
      name: "",
      photo: "",
    },
  ]);

  const [error, setError] = useError();
  const [setOpenModal, Modal] = useModal();

  React.useEffect(() => {
    const requestData = async () => {
      try {
        const response = await baseRegisterUrl.get("/employees");
        setData(response.data);
      } catch (error) {
        setError({
          error: true,
          message: "Cannot get information",
        });
      }
    };

    requestData();
  }, [setError]);

  return (
    <>
      <Modal>
        <FormEmployee />
      </Modal>
      <div className="sm:px-6 w-full">
        <div className="px-4 md:px-10 py-4 md:py-7">
          <div className="flex items-center justify-between">
            <Tittle>Employees</Tittle>
          </div>
        </div>
        <div className="bg-white py-4 md:py-7 px-4 md:px-8 xl:px-10">
          <div className="sm:flex items-center justify-between">
            <div className="flex items-center gap-4">
              <div className="rounded-full focus:outline-none focus:ring-2  focus:bg-indigo-50 focus:ring-indigo-800">
                <div className="py-2 px-8 bg-indigo-100  rounded-full">
                  {error && (
                    <p
                      className={`${
                        error.error ? "text-red-400" : "text-indigo-500"
                      }`}
                    >
                      {error.error ? error.message : "All"}
                    </p>
                  )}
                </div>
              </div>
            </div>
            <button
              className="focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600 mt-4 sm:mt-0 inline-flex items-start justify-start px-6 py-3 bg-indigo-700 hover:bg-indigo-600 focus:outline-none rounded"
              onClick={() => setOpenModal(true)}
            >
              <p className="text-sm font-medium leading-none text-white">
                New Employee
              </p>
            </button>
          </div>
          <div className="mt-7 overflow-x-auto">
            <table className="w-full whitespace-nowrap">
              <thead>
                <tr>
                  <th>Employee ID</th>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Photo</th>
                </tr>
              </thead>
              <tbody>
                {data.map((item: IEmployee) => (
                  <tr
                    key={item.employeeId}
                    className="focus:outline-none h-16 border border-gray-100 rounded-lg hover:bg-sky-100 transition-all duration-150 ease-linear text-center"
                  >
                    <td>
                      <p className="text-sm leading-none text-gray-600">
                        {item.employeeId}
                      </p>
                    </td>
                    <td className="">
                      <div className="flex items-center pl-5">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          width="20"
                          height="20"
                          viewBox="0 0 20 20"
                          fill="none"
                        >
                          <path
                            d="M9.16667 2.5L16.6667 10C17.0911 10.4745 17.0911 11.1922 16.6667 11.6667L11.6667 16.6667C11.1922 17.0911 10.4745 17.0911 10 16.6667L2.5 9.16667V5.83333C2.5 3.99238 3.99238 2.5 5.83333 2.5H9.16667"
                            stroke="#52525B"
                            strokeWidth="1.25"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                          ></path>
                          <circle
                            cx="7.50004"
                            cy="7.49967"
                            r="1.66667"
                            stroke="#52525B"
                            strokeWidth="1.25"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                          ></circle>
                        </svg>
                        <p className="text-sm leading-none text-gray-600">
                          {item.id}
                        </p>
                      </div>
                    </td>
                    <td className="">
                      <div className="flex items-center pl-5">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          width="20"
                          height="20"
                          viewBox="0 0 20 20"
                          fill="none"
                        >
                          <path
                            d="M9.16667 2.5L16.6667 10C17.0911 10.4745 17.0911 11.1922 16.6667 11.6667L11.6667 16.6667C11.1922 17.0911 10.4745 17.0911 10 16.6667L2.5 9.16667V5.83333C2.5 3.99238 3.99238 2.5 5.83333 2.5H9.16667"
                            stroke="#52525B"
                            strokeWidth="1.25"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                          ></path>
                          <circle
                            cx="7.50004"
                            cy="7.49967"
                            r="1.66667"
                            stroke="#52525B"
                            strokeWidth="1.25"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                          ></circle>
                        </svg>
                        <p className="text-sm leading-none text-gray-600">
                          {item.name}
                        </p>
                      </div>
                    </td>
                    <td className="pl-4">
                      <button className="focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600 mt-4 sm:mt-0 inline-flex items-start justify-start px-6 py-3 bg-indigo-700 hover:bg-indigo-600 focus:outline-none rounded">
                        <p className="text-sm font-medium leading-none text-white">
                          View
                        </p>
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default Employee;

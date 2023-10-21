import React from "react";
import { baseRegisterUrl } from "../utils/api";
import Tittle from "../components/Tittle";
import { ICustomer } from "../vite-env";
import useError from "../hooks/useError";
import useModal from "../hooks/useModal";
import FormNewCustomer from "../components/FormNewCustomer";

const Customer = () => {
  const [data, setData] = React.useState<ICustomer[]>([
    {
      customerId: 0,
      name: "",
      location: "",
    },
  ]);

  const [setOpenModal, Modal] = useModal();
  const [error, setError] = useError();

  React.useEffect(() => {
    const requestData = async () => {
      try {
        const response = await baseRegisterUrl.get("/customers");
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
        <FormNewCustomer />
      </Modal>
      <div className="sm:px-6 w-full">
        <div className="px-4 md:px-10 py-4 md:py-7">
          <div className="flex items-center justify-between">
            <Tittle>Customers</Tittle>
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
              className="focus:ring-2 focus:ring-offset-2  mt-4 sm:mt-0 inline-flex items-start justify-start px-6 py-3 bg-indigo-700 hover:bg-indigo-600 focus:outline-none rounded"
              onClick={() => setOpenModal(true)}
            >
              <p className="text-sm font-medium leading-none text-white">
                New Customer
              </p>
            </button>
          </div>
          <div className="mt-7 overflow-x-auto">
            <table className="w-full whitespace-nowrap">
              <thead>
                <tr>
                  <th>Customer ID</th>
                  <th>Name</th>
                  <th>Location</th>
                </tr>
              </thead>
              <tbody>
                {data.map((item: ICustomer) => (
                  <tr
                    key={item.customerId}
                    className="focus:outline-none h-16 border border-gray-100 rounded-lg hover:bg-sky-100 transition-all duration-150 ease-linear text-center"
                  >
                    <td>
                      <p className="text-sm leading-none text-gray-600">
                        {item.customerId}
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
                          {item.name}
                        </p>
                      </div>
                    </td>
                    <td className="pl-24">
                      <div className="flex items-center">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          strokeWidth={1.5}
                          stroke="currentColor"
                          className="w-6 h-6 text-gray-600"
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M17.982 18.725A7.488 7.488 0 0012 15.75a7.488 7.488 0 00-5.982 2.975m11.963 0a9 9 0 10-11.963 0m11.963 0A8.966 8.966 0 0112 21a8.966 8.966 0 01-5.982-2.275M15 9.75a3 3 0 11-6 0 3 3 0 016 0z"
                          />
                        </svg>

                        <p className="text-sm leading-none text-gray-600 ml-2">
                          {item.location}
                        </p>
                      </div>
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

export default Customer;

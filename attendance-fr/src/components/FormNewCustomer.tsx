import React from "react";
import useError from "../hooks/useError";
import { ICustomer } from "../vite-env";
import { baseRegisterUrl } from "../utils/api";

const FormNewCustomer = () => {
  const [customer, setCustomer] = React.useState<ICustomer>({
    customerId: 0,
    name: "",
    location: "",
  });

  const [, setError, ErrorComponent] = useError();

  const handleChangeForm = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCustomer({
      ...customer,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const response = await baseRegisterUrl.post("/customers/save", {
        name: customer.name,
        location: customer.location,
      }, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.status !== 201)
        throw new Error("Error al crear el customer");

      setError({ error: false, message: "Customer created" });

      setTimeout(() => {
        window.location.reload();
      }, 1200);
    } catch (error) {
      console.log(error);
      setError({ error: true, message: "Cannot create customer" });
    }
  };

  return (
    <>
      <ErrorComponent />
      <form onSubmit={handleSubmit}>
        <div className="flex flex-wrap -mx-3 mb-2">
          <div className="w-full md:w-1/2 px-3">
            <label
              className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
              htmlFor="name"
            >
              Customer Name
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
          <div className="w-full md:w-1/2 px-3">
            <label
              className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
              htmlFor="name"
            >
              Location
            </label>
            <input
              className="appearance-none block w-full bg-gray-200 text-gray-700 border rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white "
              id="location"
              name="location"
              type="text"
              placeholder="Calle 12D"
              onChange={handleChangeForm}
            />
          </div>
        </div>
        <button
          className="w-full bg-indigo-600 p-2 mb-3 text-white rounded hover:bg-indigo-500"
          disabled={customer.name === "" || customer.location === ""}
          type="submit"
        >
          Create Customer
        </button>
      </form>
    </>
  );
};

export default FormNewCustomer;

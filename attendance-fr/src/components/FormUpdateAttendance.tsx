import useError from "../hooks/useError";
import { baseAttendanceUrl } from "../utils/api";
import { IAttendance } from "../vite-env";

const FormUpdateAttendance = ({ attendance }: { attendance: IAttendance }) => {
  const [, setError, ErrorComponent] = useError();

  if (!attendance) return;

  const parseDate = (dateString: string): string => {
    const date = new Date(Date.parse(dateString));

    const year = date.getFullYear();
    const month = date.getMonth() + 1; // Nota: Los monthes comienzan desde 0, por lo que sumamos 1
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes();
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await baseAttendanceUrl.put(
        "/attendance/check-out",
        attendance
      );

      if (response.status !== 200) throw new Error("Error al hacer check out");
      setError({
        error: false,
        message: "Check out completed",
      });

      setTimeout(() => {
        window.location.reload();
      }, 1200);
    } catch (error) {
      setError({
        error: true,
        message: "Check out failed",
      });
    }
  };

  return (
    <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
      <ErrorComponent />
      <div className="flex gap-3">
        <label htmlFor="attendanceId">Attendance ID</label>
        <input
          type="text"
          name="attendanceId"
          value={attendance.attendanceId}
          disabled
          className="w-10 text-center text-sm p-1 bg-gray-200 rounded-md"
        />
      </div>
      <div className="flex gap-3">
        <label htmlFor="employeeId">Employee ID</label>
        <input
          type="text"
          id="employeeId"
          name="employeeId"
          value={attendance.employeeId}
          disabled
          className="w-10 text-center text-sm p-1 bg-gray-200 rounded-md"
        />
      </div>
      <div className="flex gap-3">
        <label htmlFor="customerId">Customer ID</label>
        <input
          type="text"
          name="customerId"
          value={attendance.customerId}
          disabled
          className="w-10 text-center text-sm p-1 bg-gray-200 rounded-md"
        />
      </div>
      <div className="flex gap-3">
        <label htmlFor="timeIn">Time In</label>
        <input
          type="text"
          name="timeIn"
          value={parseDate(attendance.timeIn)}
          disabled
          className="w-1/3 text-center text-sm p-1 bg-gray-200 rounded-md outline-none"
        />
      </div>
      <div className="flex gap-3">
        <label htmlFor="timeOut">Time Out</label>
        <input
          type="text"
          name="timeOut"
          value={attendance.timeOut ? parseDate(attendance.timeOut) : ""}
          disabled
          className="w-1/3 text-center text-sm p-1 bg-gray-200 rounded-md outline-none"
        />
        {!attendance.timeOut && (
          <button className="bg-indigo-600 rounded-lg p-2 text-white text-sm hover:bg-indigo-500 hover:text-slate-200 transition-all ease-in-out duration-100">
            Check out
          </button>
        )}
      </div>
    </form>
  );
};

export default FormUpdateAttendance;

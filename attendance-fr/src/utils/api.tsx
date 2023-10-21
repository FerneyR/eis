import axios from "axios";

const baseRegisterUrl = axios.create({
  baseURL: import.meta.env.VITE_REGISTER_URL,
});

const baseAttendanceUrl = axios.create({
  baseURL: import.meta.env.VITE_ATTENDANCE_URL,
});

const baseMinioUrl = axios.create({
  baseURL: import.meta.env.VITE_MINIO_URL,
});

export { baseAttendanceUrl, baseRegisterUrl, baseMinioUrl };

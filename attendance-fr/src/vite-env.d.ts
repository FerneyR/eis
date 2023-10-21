/// <reference types="vite/client" />

export interface ICustomer {
  customerId: number;
  name: string;
  location: string;
}

export interface IEmployee {
  employeeId: number;
  id: string;
  name: string;
  photo: string;
}

export interface IModal {
  openModal: boolean;
  setOpenModal: React.Dispatch<React.SetStateAction<boolean>>;
  children?: string | JSX.Element | JSX.Element[] | (() => JSX.Element);
}

export interface IAssignment {
  assignmentId: number;
  customerId: number;
  employeeId: number;
  startDate: string;
  endDate: string | null;
}

export interface IAttendance {
  attendanceId: number;
  customerId: number;
  employeeId: number;
  timeIn: string;
  timeOut: string;
  photo: string;
  location: string;
}

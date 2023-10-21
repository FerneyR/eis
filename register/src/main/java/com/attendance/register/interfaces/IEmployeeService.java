package com.attendance.register.interfaces;

import com.attendance.register.dto.EmployeeDTO;
import com.attendance.register.models.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee save(EmployeeDTO employeeDTO);

    List<Employee> getEmployees();

}

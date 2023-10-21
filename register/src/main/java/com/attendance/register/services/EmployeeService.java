package com.attendance.register.services;

import com.attendance.register.dto.EmployeeDTO;
import com.attendance.register.interfaces.IEmployeeService;
import com.attendance.register.models.Employee;
import com.attendance.register.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        Employee employeeToSave = new Employee();
        employeeToSave.setId(employeeDTO.getId());
        employeeToSave.setName(employeeDTO.getName());
        employeeToSave.setPhoto(employeeDTO.getPhoto());
        return  employeeRepository.save(employeeToSave);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

}

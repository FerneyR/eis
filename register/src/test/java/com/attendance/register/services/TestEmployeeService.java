package com.attendance.register.services;

import com.attendance.register.dto.EmployeeDTO;
import com.attendance.register.interfaces.IAssignmentService;
import com.attendance.register.models.Assignment;
import com.attendance.register.models.Customer;
import com.attendance.register.models.Employee;
import com.attendance.register.repositories.AssignmentRepository;
import com.attendance.register.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TestEmployeeService {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private IAssignmentService assignmentService;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testListEmployeeService() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setName("Employee Name");
        employee.setPhoto("Employee Photo");

        employees.add(employee);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> employeesList = employeeService.getEmployees();

        assertEquals(employees.get(0).getEmployeeId(), employeesList.get(0).getEmployeeId());
    }

    //TODO: El servicio no debería recibir un DTO sino un Employee de la capa de modelo y un customer ID
    @Test
    void testSaveEmployeeService() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId("1");
        employeeDTO.setName("Employee Name");
        employeeDTO.setPhoto("Employee Photo");
        employeeDTO.setIdCustomer(1);

        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setName("Employee Name");
        employee.setPhoto("Employee Photo");

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Customer Name");
        customer.setLocation("Customer Address");

        Assignment assignment = new Assignment();
        assignment.setAssignmentId(1);
        assignment.setCustomer(customer);
        assignment.setEmployee(employee);
        assignment.setStartDate(Timestamp.valueOf("2023-10-20 00:00:00.0"));
        assignment.setEndDate(Timestamp.valueOf("2023-10-20 00:00:00.1"));

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee employeeSaved = employeeService.save(employeeDTO);

        assertEquals(employeeSaved.getEmployeeId(), employee.getEmployeeId());
    }
}

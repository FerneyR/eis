package com.attendance.register.controllers;

import com.attendance.register.dto.EmployeeDTO;
import com.attendance.register.interfaces.IEmployeeService;
import com.attendance.register.models.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(EmployeeController.class)
class TestEmployeeController {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @MockBean
    private IEmployeeService employeeService;

    @Test
    void testSave() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setName("Employee Name");
        employee.setPhoto("Employee Photo");

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId("1");
        employeeDTO.setName("Employee Name");
        employeeDTO.setPhoto("Employee Photo");
        employeeDTO.setIdCustomer(1);

        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employee);


        mockMvc.perform(MockMvcRequestBuilders.post("/employees/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"idCustomer\":1,\"name\": \"Test Name\",\"photo\":\"Photo\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(1));
    }

    @Test
    void testFindAll() throws Exception {

        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setName("Employee Name");
        employee.setPhoto("Employee Photo");

        employees.add(employee);

        when(employeeService.getEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeId").value(1));

    }
}

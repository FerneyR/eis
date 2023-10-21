package com.attendance.register.repositories;

import com.attendance.register.models.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
class TestEmployeeRepository {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testEmployeeRepository() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setName("Employee Name");
        employee.setPhoto("Employee Photo");

        Employee employeeSaved = employeeRepository.save(employee);

        assertNotNull(employeeSaved);
    }
}

package com.attendance.register.repositories;

import com.attendance.register.models.Assignment;
import com.attendance.register.models.Customer;
import com.attendance.register.models.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
class TestAssigmentRepository {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testAssignmentRepository() {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        customerRepository.save(customer);

        Employee employee = new Employee();
        employee.setEmployeeId(1);

        employeeRepository.save(employee);

        Assignment assignment = new Assignment();
        assignment.setAssignmentId(1);
        assignment.setCustomer(customer);
        assignment.setEmployee(employee);
        assignment.setStartDate(Timestamp.valueOf("2023-10-20 00:00:00.0"));
        assignment.setEndDate(Timestamp.valueOf("2023-10-20 00:00:00.1"));

        Assignment assignmentSaved = assignmentRepository.save(assignment);

        assertNotNull(assignmentSaved);
        assertEquals(assignmentSaved.getAssignmentId(), assignment.getAssignmentId());
    }

}

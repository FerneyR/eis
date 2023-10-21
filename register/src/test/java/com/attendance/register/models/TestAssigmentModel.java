package com.attendance.register.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class TestAssigmentModel {
    @Test
    void testAssigmentEntity() {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        Employee employee = new Employee();
        employee.setEmployeeId(1);

        Assignment assignment = new Assignment();
        assignment.setAssignmentId(1);
        assignment.setCustomer(customer);
        assignment.setEmployee(employee);
        assignment.setStartDate(Timestamp.valueOf("2023-10-20 00:00:00.0"));
        assignment.setEndDate(Timestamp.valueOf("2023-10-20 00:00:00.1"));

        assertEquals(1, assignment.getAssignmentId());
        assertEquals(customer, assignment.getCustomer());
        assertEquals(employee, assignment.getEmployee());
        assertEquals(Timestamp.valueOf("2023-10-20 00:00:00.0"), assignment.getStartDate());
        assertEquals(Timestamp.valueOf("2023-10-20 00:00:00.1"), assignment.getEndDate());
    }
}

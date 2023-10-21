package com.attendance.register.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class TestEmployeeModel {
    @Test
    void testEmployeeEntity() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setId("1");
        employee.setName("Name");
        employee.setPhoto("Photo");

        assertEquals(1, employee.getEmployeeId());
        assertEquals("1", employee.getId());
        assertEquals("Name", employee.getName());
        assertEquals("Photo", employee.getPhoto());
    }
}

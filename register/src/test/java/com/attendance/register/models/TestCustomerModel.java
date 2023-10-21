package com.attendance.register.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class TestCustomerModel {
    @Test
    void testCustomerEntity() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Name");
        customer.setLocation("Location");

        assertEquals(1, customer.getCustomerId());
        assertEquals("Name", customer.getName());
        assertEquals("Location", customer.getLocation());
    }
}

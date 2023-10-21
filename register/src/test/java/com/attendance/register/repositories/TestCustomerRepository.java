package com.attendance.register.repositories;

import com.attendance.register.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
class TestCustomerRepository {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testCustomerRepository() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Customer Name");
        customer.setLocation("Customer Address");

        Customer customerSaved = customerRepository.save(customer);

        assertNotNull(customerSaved);
    }
}

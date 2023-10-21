package com.attendance.register.services;

import com.attendance.register.models.Customer;
import com.attendance.register.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TestCustomerService {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testListCustomerService() {

        List<Customer> customers = new ArrayList<>();

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Customer Name");
        customer.setLocation("Customer Address");

        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> customersList = customerService.getCustomers();

        assertEquals(customers.get(0).getCustomerId(), customersList.get(0).getCustomerId());
    }
}

package com.attendance.register.controllers;

import com.attendance.register.interfaces.ICustomerService;
import com.attendance.register.models.Customer;
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

import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(CustomerController.class)
class TestCustomerController {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CustomerController customerController;

    @MockBean
    private ICustomerService customerService;

    @Test
    void testSave() throws Exception {
        List<Customer> customers = new ArrayList<>();

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Customer Name");
        customer.setLocation("Customer Address");

        customers.add(customer);

        when(customerService.getCustomers()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(1));
    }

}

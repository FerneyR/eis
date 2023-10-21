package com.attendance.register.interfaces;

import com.attendance.register.models.Customer;

import java.util.List;

public interface ICustomerService {

    List<Customer> getCustomers();
    Customer save(Customer customer);
}

package com.attendance.register.controllers;

import com.attendance.register.interfaces.ICustomerService;
import com.attendance.register.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

     @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody Customer customer){
        customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer saved");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAll(){
        return  ResponseEntity.ok(customerService.getCustomers());
    }

}
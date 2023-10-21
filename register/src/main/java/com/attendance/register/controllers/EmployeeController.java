package com.attendance.register.controllers;

import com.attendance.register.dto.EmployeeDTO;
import com.attendance.register.interfaces.IEmployeeService;
import com.attendance.register.models.Employee;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("employees")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @PostMapping("save")
    public ResponseEntity<Employee> save(@RequestBody EmployeeDTO employeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employeeDto));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getEmployees());
    }

}

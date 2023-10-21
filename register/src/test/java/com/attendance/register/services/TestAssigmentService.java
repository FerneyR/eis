package com.attendance.register.services;

import com.attendance.register.models.Assignment;
import com.attendance.register.models.Customer;
import com.attendance.register.models.Employee;
import com.attendance.register.repositories.AssignmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TestAssigmentService {
    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void testSaveAssignmentService() {
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

        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        boolean done = false;
        try {
            assignmentService.save(assignment);
            done = true;
        }catch (Exception e){
            System.out.println("Error!");
        }

        assertTrue(done);

    }
}

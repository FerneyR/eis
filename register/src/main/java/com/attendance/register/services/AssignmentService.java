package com.attendance.register.services;

import com.attendance.register.interfaces.IAssignmentService;
import com.attendance.register.models.Assignment;
import com.attendance.register.repositories.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService {

    private final AssignmentRepository assignmentRepository;

    public void save(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

}

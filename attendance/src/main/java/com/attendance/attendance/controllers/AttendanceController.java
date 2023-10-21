package com.attendance.attendance.controllers;

import com.attendance.attendance.dto.AttendanceDTO;
import com.attendance.attendance.interfaces.IAttendanceService;
import com.attendance.attendance.models.Attendance;

import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttendanceController {

    private final IAttendanceService attendanceService;
    @GetMapping
    public List<Attendance> findAll() {
        return attendanceService.getAttendance();
    }

    @PostMapping
    public ResponseEntity<Attendance> save(@RequestBody AttendanceDTO attendanceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.save(attendanceDto));
    }

    @GetMapping
    public List<Attendance> getAllEmployees(){
        return attendanceService.findAll();
    }

    @PutMapping("/check-out")
    public ResponseEntity<Attendance> update(@RequestBody Attendance attendance){
        attendance.setTimeOut(new Timestamp(System.currentTimeMillis()));
        Attendance attendanceToUpdate = attendanceService.update(attendance);
        if(attendanceToUpdate == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else 
            return ResponseEntity.status(HttpStatus.OK).body(attendanceToUpdate);
    }

}

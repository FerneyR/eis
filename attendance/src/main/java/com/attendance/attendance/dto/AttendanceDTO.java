package com.attendance.attendance.dto;

import lombok.Data;

@Data
public class AttendanceDTO {
    private int customerId;
    private int employeeId;
    private String photo;
    private String location;
}

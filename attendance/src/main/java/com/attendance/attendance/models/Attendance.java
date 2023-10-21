package com.attendance.attendance.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attendance_id")
    private long attendanceId;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "time_in")
    private Timestamp timeIn;

    @Column(name = "time_out")
    private Timestamp timeOut;

    @Column(name = "photo")
    private String photo;

    @Column(name = "location")
    private String location;
}

package com.attendance.attendance.interfaces;

import java.util.List;

import com.attendance.attendance.dto.AttendanceDTO;
import com.attendance.attendance.models.Attendance;

public interface IAttendanceService {
    Attendance save(AttendanceDTO attendanceDTO);
    List<Attendance> findAll();
    Attendance findByIdAttendance(long attendaceId);
    Attendance update(Attendance attendance);
}

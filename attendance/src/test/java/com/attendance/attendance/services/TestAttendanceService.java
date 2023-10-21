package com.attendance.attendance.services;

import com.attendance.attendance.dto.AttendanceDTO;
import com.attendance.attendance.models.Attendance;
import com.attendance.attendance.repositories.AttendanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TestAttendanceService {
    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private AttendanceService attendanceService;


    @Test
    void testOutAttendanceService() {
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(1);
        attendance.setIdCustomer(1);
        attendance.setIdEmployee(1);
        attendance.setTimeIn(java.sql.Timestamp.valueOf("2023-10-20 00:00:00.0"));
        attendance.setTimeOut(java.sql.Timestamp.valueOf("2023-10-20 00:00:00.1"));
        attendance.setPhoto("Photo");

        when(attendanceRepository.findByCustomerAndEmployee(any(Long.class), any(Long.class))).thenReturn(attendance);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setIdCustomer(1);
        attendanceDTO.setIdEmployee(1);
        attendanceDTO.setPhoto("Photo");

        String attendanceSaved = attendanceService.save(attendanceDTO);

        assertEquals(attendanceSaved, "El empleado con el id " + attendanceDTO.getIdEmployee() + " salió a las: " + attendance.getTimeOut());
    }

    @Test
    void testInAttendanceService() {
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(1);
        attendance.setIdCustomer(1);
        attendance.setIdEmployee(1);
        attendance.setTimeIn(java.sql.Timestamp.valueOf("2023-10-20 00:00:00.0"));
        attendance.setTimeOut(null);
        attendance.setPhoto("Photo");

        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setIdCustomer(1);
        attendanceDTO.setIdEmployee(1);
        attendanceDTO.setPhoto("Photo");

        String attendanceSaved = attendanceService.save(attendanceDTO);

        assertEquals(attendanceSaved, "El empleado con el id " + attendanceDTO.getIdEmployee() + " ingresó a las: " + attendance.getTimeIn());
    }


}

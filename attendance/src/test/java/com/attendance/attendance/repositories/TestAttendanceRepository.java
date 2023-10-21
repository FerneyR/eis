package com.attendance.attendance.repositories;

import com.attendance.attendance.models.Attendance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
class TestAttendanceRepository {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    void testAttendanceRepository() {
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(1);
        attendance.setIdCustomer(1);
        attendance.setIdEmployee(1);
        attendance.setTimeIn(Timestamp.valueOf("2023-10-20 00:00:00.0"));
        attendance.setTimeOut(Timestamp.valueOf("2023-10-20 00:00:00.1"));
        attendance.setPhoto("Photo");

        Attendance attendanceSaved = attendanceRepository.save(attendance);

        assertNotNull(attendanceSaved);
        assertEquals(attendanceSaved.getAttendanceId(), attendance.getAttendanceId());
    }
}

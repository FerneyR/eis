package com.attendance.attendance.model;

import com.attendance.attendance.models.Attendance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class TestAttendanceModel {

    @Test
    void testAttendanceEntity() {
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(1);
        attendance.setIdCustomer(1);
        attendance.setIdEmployee(1);
        attendance.setTimeIn(Timestamp.valueOf("2023-10-20 00:00:00.0"));
        attendance.setTimeOut(Timestamp.valueOf("2023-10-20 00:00:00.1"));
        attendance.setPhoto("Photo");

        assertEquals(1, attendance.getAttendanceId());
        assertEquals(1, attendance.getIdCustomer());
        assertEquals(1, attendance.getIdEmployee());
        assertEquals(Timestamp.valueOf("2023-10-20 00:00:00.0"), attendance.getTimeIn());
        assertEquals(Timestamp.valueOf("2023-10-20 00:00:00.1"), attendance.getTimeOut());
        assertEquals("Photo", attendance.getPhoto());
    }
}

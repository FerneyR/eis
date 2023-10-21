package com.attendance.attendance.repositories;

import com.attendance.attendance.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // @Query(value = "SELECT a FROM Attendance a WHERE a.idCustomer = ?1 and a.idEmployee = ?2 and a.timeIn is not null and a.timeOut = null")
    // Attendance findByCustomerAndEmployee(long idCustomer, long idEmployee);

    @Query(value = "SELECT * FROM attendance a WHERE a.attendance_id = ?1", nativeQuery = true)
    Attendance findByAttendanceId(long attendanceId);

    List<Attendance> findAllByStatus(int status);

}

package com.attendance.attendance.services;

import com.attendance.attendance.dto.AttendanceDTO;
import com.attendance.attendance.interfaces.IAttendanceService;
import com.attendance.attendance.models.Attendance;
import com.attendance.attendance.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService implements IAttendanceService {

    private final AttendanceRepository attendanceRepository;
    public List<Attendance> getAttendance() {
        return attendanceRepository.findAll();
    }
    @Override
    public Attendance save(AttendanceDTO attendanceDTO) {
        Attendance attendanceToCreate = new Attendance();
        attendanceToCreate.setCustomerId(attendanceDTO.getCustomerId());
        attendanceToCreate.setEmployeeId(attendanceDTO.getEmployeeId());
        attendanceToCreate.setTimeIn(new Timestamp(System.currentTimeMillis()));
        attendanceToCreate.setPhoto(attendanceDTO.getPhoto());
        attendanceToCreate.setLocation(attendanceDTO.getLocation());
        return attendanceRepository.save(attendanceToCreate);
    }


    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }   

    @Override
    public Attendance findByIdAttendance(long attendaceId){
        return attendanceRepository.findByAttendanceId(attendaceId);
      }
    
    @Override
    public Attendance update(Attendance attendance){
        Attendance attendanceToUpdate = findByIdAttendance(attendance.getAttendanceId());
        if(attendanceToUpdate == null || attendanceToUpdate.getTimeOut() != null){
            return null;
        }
        attendanceToUpdate.setCustomerId(attendance.getCustomerId());
        attendanceToUpdate.setEmployeeId(attendance.getEmployeeId());
        attendanceToUpdate.setTimeIn(attendance.getTimeIn());
        attendanceToUpdate.setTimeOut(attendance.getTimeOut());
        return attendanceRepository.save(attendanceToUpdate);
    }


}

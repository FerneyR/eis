package com.attendance.attendance.services;

import com.attendance.attendance.interfaces.IAlertsService;
import com.attendance.attendance.interfaces.IEmailService;
import com.attendance.attendance.models.Attendance;
import com.attendance.attendance.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertsService implements IAlertsService {

    private final AttendanceRepository attendanceRepository;
    private final IEmailService emailService;

    @Value("${mail.sender.recipient}")
    private String recipient;

    @Override
    public void sendAlertsToUserWithAnomalies() {
        List<Attendance> attendances;
        try {
            attendances = attendanceRepository.findAllByStatus(0);
            if (!attendances.isEmpty()) {
                for (Attendance attendance : attendances) {
                    emailService.sendEmail(recipient, mailMessage(attendance.getIdEmployee()));
                    attendance.setStatus(2);
                    attendanceRepository.save(attendance);
                }
                log.info("There are anomalies to send alerts!");
            } else {
                log.info("There are no anomalies to send alerts!");
            }
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
    }

    private String mailMessage(long idEmployee) {
        return MessageFormat.format("The user with ID {0} has anomalies in his attendance, please check the system.", idEmployee);
    }

}

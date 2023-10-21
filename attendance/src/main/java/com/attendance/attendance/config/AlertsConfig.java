package com.attendance.attendance.config;


import com.attendance.attendance.interfaces.IAlertsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class AlertsConfig {

    private final IAlertsService alertsService;

    @Scheduled(cron = "${send.alerts.cron}")
    public void sendAlerts() throws MessagingException {
        alertsService.sendAlertsToUserWithAnomalies();
    }

}

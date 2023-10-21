package com.attendance.attendance.interfaces;

import javax.mail.MessagingException;

public interface IAlertsService {

    void sendAlertsToUserWithAnomalies() throws MessagingException;

}

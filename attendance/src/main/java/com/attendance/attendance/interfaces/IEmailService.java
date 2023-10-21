package com.attendance.attendance.interfaces;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface IEmailService {

    void sendEmail(String recipient, String message) throws MessagingException;

}

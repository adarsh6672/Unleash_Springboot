package com.unleash.notification_service.service;

import com.unleash.base_domain.Dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }


    public void sendMailtoUser(NotificationDto notification){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(notification.getMailId());
        mailMessage.setSubject(notification.getSubject());
        mailMessage.setText("Dear "+notification.getFullName()+" , " +notification.getMessage());
        try {
            sendEmail(mailMessage);
        }catch (Exception e){
            System.out.println("email sending failed");

        }
    }
}

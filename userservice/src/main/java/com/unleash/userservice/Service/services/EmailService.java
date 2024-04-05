package com.unleash.userservice.Service.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendEmail(SimpleMailMessage email);
}

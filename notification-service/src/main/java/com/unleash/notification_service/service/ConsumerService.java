package com.unleash.notification_service.service;



import com.unleash.base_domain.Dto.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class ConsumerService {

    @Autowired
    private EmailService emailService;

    Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @KafkaListener(topics = "unleash-notification",groupId = "notification-group")
    public void consumeEvents(NotificationDto notification) {
        log.info("consumer consume the events {} ", notification.toString());
        emailService.sendMailtoUser(notification);

    }

}

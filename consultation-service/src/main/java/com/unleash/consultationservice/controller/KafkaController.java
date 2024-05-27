package com.unleash.consultationservice.controller;

import com.unleash.base_domain.Dto.NotificationDto;
import com.unleash.consultationservice.Service.KafkaPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaPublisherService kafkaPublisherService;

    @PostMapping("/publish")
    public void sendEvents(@RequestBody NotificationDto notificationDTO) {
        kafkaPublisherService.sendEventsToTopic(notificationDTO);
    }
}

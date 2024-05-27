package com.unleash.consultationservice.Service;

import com.unleash.base_domain.Dto.NotificationDto;
import com.unleash.consultationservice.DTO.UserDto;
import com.unleash.consultationservice.Interface.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaPublisherService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<String, Object>> future = template.send("unleash-notification", message);
        future.whenComplete((result,ex)->{
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });

    }

    public void sendEventsToTopic(NotificationDto notificationDTO) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("unleash-notification", notificationDTO);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + notificationDTO.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            notificationDTO.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }
    }

    public void createNotification(NotificationDto notificationDto){
        try{
            UserDto userDto = userClient.getUserWithUserId(notificationDto.getUserId()).getBody();
            if(userDto!=null){
                notificationDto.setFullName(userDto.getFullname());
                notificationDto.setMailId(userDto.getEmail());
                sendEventsToTopic(notificationDto);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

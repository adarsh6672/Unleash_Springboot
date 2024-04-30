package com.unleash.consultationservice.Chat.Controller;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.unleash.consultationservice.Chat.Dto.CallUserRequest;
import com.unleash.consultationservice.Chat.Model.ChatMessage;
import com.unleash.consultationservice.Chat.Model.ChatNotification;
import com.unleash.consultationservice.Chat.Services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public ChatNotification processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        ChatNotification notification=new ChatNotification(
                savedMsg.getId(),
                savedMsg.getSenderId(),
                savedMsg.getRecipientId(),
                savedMsg.getContent());
        messagingTemplate.convertAndSendToUser(
                String.valueOf(savedMsg.getRecipientId()), "/queue/messages", notification);

        return notification;
    }

    @GetMapping("/ws/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable int senderId,
                                                              @PathVariable int recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @MessageMapping("/videocall")
    public void requestVideoCall(@Payload CallUserRequest request){
            messagingTemplate.convertAndSendToUser(
                    request.getData().getUserToCall(),"/queue/messages", request
            );
    }
}

package com.unleash.consultationservice.Chat.Repository;

import com.unleash.consultationservice.Chat.Model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage , Integer> {
    List<ChatMessage> findByChatId(String s);
}

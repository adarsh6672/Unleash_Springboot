package com.unleash.consultationservice.Chat.Repository;

import com.unleash.consultationservice.Chat.Model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom , Integer> {

   Optional<ChatRoom> findBySenderIdAndRecipientId(int senderId, int recipientId);
}

package com.unleash.consultationservice.Chat.Repository;

import com.unleash.consultationservice.Chat.Model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepo  extends JpaRepository<ChatUser , Integer> {

}

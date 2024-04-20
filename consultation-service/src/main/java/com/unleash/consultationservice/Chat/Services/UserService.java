package com.unleash.consultationservice.Chat.Services;

import com.unleash.consultationservice.Chat.Model.ChatUser;
import com.unleash.consultationservice.Chat.Repository.ChatUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ChatUserRepo chatUserRepo;

    public void saveUser(ChatUser user) {
        chatUserRepo.save(user);
    }

    public void disconnect(ChatUser user) {
        ChatUser storedUser = chatUserRepo.findById(user.getNickId()).orElse(null);
        if (storedUser != null) {
            chatUserRepo.save(storedUser);
        }
    }

    public List<ChatUser> findConnectedUsers() {
        return chatUserRepo.findAll();
    }

}

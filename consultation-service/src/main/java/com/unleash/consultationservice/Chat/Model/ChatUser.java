package com.unleash.consultationservice.Chat.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ChatUser {
    @Id
    private int nickId;
    private String fullName;

}

package com.unleash.consultationservice.Chat.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CallUserRequest {
    private String type;
    private CallUserData data;
}

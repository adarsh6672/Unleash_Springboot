package com.unleash.consultationservice.Chat.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CallUserData {
    private String userToCall;
    private SignalingData signalData;
    private String from;
    private String name;
}

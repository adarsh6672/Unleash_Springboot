package com.unleash.consultationservice.Chat.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignalingData {

        private String type;
        private String sdp;
}

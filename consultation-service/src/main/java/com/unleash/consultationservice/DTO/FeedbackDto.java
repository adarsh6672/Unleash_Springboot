package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackDto {

    private int rating;

    private String feedback;

    private int sessionId;
}

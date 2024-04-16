package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingResponseDto {

    int sessionId;
    String counselor;
    LocalDateTime bookedOn;
}

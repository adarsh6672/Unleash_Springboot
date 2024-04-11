package com.unleash.userservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AvilabilityDto {

    int id;

    private int userId;

    private LocalDateTime slot;

    private boolean isBooked;

}

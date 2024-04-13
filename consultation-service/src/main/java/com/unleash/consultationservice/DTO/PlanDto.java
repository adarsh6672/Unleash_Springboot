package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class PlanDto {
    int id;

    String planeName;

    String iconUrl;

    int noOfSession;

    String description;

    BigDecimal price;
}

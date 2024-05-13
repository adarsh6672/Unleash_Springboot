package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PromocodeDTO {

    private String promocode;

    private BigDecimal discountAmount;

    private BigDecimal minimumAmount;

    private String  startDate;

    private String  endDate;
}

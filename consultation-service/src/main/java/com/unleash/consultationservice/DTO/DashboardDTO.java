package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DashboardDTO {
    private int totalCounselors;
    private int totalPatients;
    private int activeSubscribers;
    private BigDecimal todayIncome;
}
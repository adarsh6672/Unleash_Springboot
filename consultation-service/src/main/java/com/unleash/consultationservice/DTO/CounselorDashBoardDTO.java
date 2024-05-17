package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CounselorDashBoardDTO {
    private int todaysSession;
    private int totalSession;
    private String totalIncome;
    private String lastWeekPending;
}

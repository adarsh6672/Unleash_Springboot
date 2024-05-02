package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CounselorPaymentProcess {
    private int counselorid;
    private String counselorName;
    private int sessionCount;
    private int totalAmount;
}

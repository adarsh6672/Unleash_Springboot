package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDto {

    private String paymentId;
    private String orderId;
    private int planId;
}

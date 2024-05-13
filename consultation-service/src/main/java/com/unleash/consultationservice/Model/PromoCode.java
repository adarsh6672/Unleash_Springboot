package com.unleash.consultationservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String promocode;

    private BigDecimal discountAmount;

    private BigDecimal minimumAmount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean isActive;

}

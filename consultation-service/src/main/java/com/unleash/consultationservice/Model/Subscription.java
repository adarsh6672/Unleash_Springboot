package com.unleash.consultationservice.Model;

import com.razorpay.Plan;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plans plans;

    private int sessionCount;

    private LocalDateTime startFrom;

    private LocalDateTime endOn;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private SubscriptionPayments subscriptionPayments;

    @ManyToOne
    @JoinColumn(name = "promocode_id")
    private PromoCode promoCode;

}

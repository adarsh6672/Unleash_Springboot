package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.SubscriptionPayments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPaymentRepo extends JpaRepository<SubscriptionPayments , Integer> {

    SubscriptionPayments findByOrderId(String orderId);



}

package com.unleash.consultationservice.Service.serviceInterface;

import com.razorpay.RazorpayException;
import com.unleash.consultationservice.DTO.PaymentDto;

import java.util.List;

public interface SubscriptionService {
    List findAllPlans();

    String createOrder(String amount , int id) throws RazorpayException;

    boolean updateOrder(PaymentDto dto);

    boolean isSubscribed(int userId);
}

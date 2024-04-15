package com.unleash.consultationservice.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.unleash.consultationservice.DTO.PaymentDto;
import com.unleash.consultationservice.Service.serviceInterface.SubscriptionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultation/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/payment/{amount}")
    public String Payment(@PathVariable String amount,@RequestHeader ("userId") int userId) throws RazorpayException {

        return subscriptionService.createOrder(amount, userId);
    }

    @PutMapping("/payment/update")
    public ResponseEntity<?> updatePayment(@RequestBody PaymentDto dto){
        if(subscriptionService.updateOrder(dto)){
            return ResponseEntity.ok().body("Subscription Sussess");
        }
        return ResponseEntity.notFound().build();
    }
}

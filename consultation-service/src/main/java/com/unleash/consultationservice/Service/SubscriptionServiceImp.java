package com.unleash.consultationservice.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.unleash.consultationservice.DTO.PaymentDto;
import com.unleash.consultationservice.Model.Plans;
import com.unleash.consultationservice.Model.Subscription;
import com.unleash.consultationservice.Model.SubscriptionPayments;
import com.unleash.consultationservice.Repository.PlanRepo;
import com.unleash.consultationservice.Repository.SubscriptionPaymentRepo;
import com.unleash.consultationservice.Repository.SubscriptionRepo;
import com.unleash.consultationservice.Service.serviceInterface.SubscriptionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class SubscriptionServiceImp implements SubscriptionService {

    @Autowired
    private PlanRepo planRepo;

    @Autowired
    private SubscriptionPaymentRepo subscriptionPaymentRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Value("${razorpay.key_id}")
    private String key;

    @Value("${razorpay.secret_id}")
    private String secret;

    @Override
    public List findAllPlans(){
        return planRepo.findByIsHiddenFalse();
    }

    @Override
    public String createOrder(String amount , int userId) throws RazorpayException {

        RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_receipt_11");

        Order order = razorpayClient.orders.create(orderRequest);
        System.out.println(order);
        String orderId = order.get("id");

        try{
            SubscriptionPayments payments =  new SubscriptionPayments();
            BigDecimal newAmount = new BigDecimal(String.valueOf(amount));
            BigDecimal finalAmount = newAmount.divide(BigDecimal.valueOf(100));
            payments.setAmount(finalAmount);
            payments.setOrderId(orderId);
            payments.setUserId(userId);
            subscriptionPaymentRepo.save(payments);

        }catch (Exception e){
            e.printStackTrace();
        }

        return orderId;
    }

    @Override
    public boolean updateOrder(PaymentDto dto) {
        String orderId = dto.getOrderId();
        SubscriptionPayments payments;

        try{
            Plans plans = planRepo.findById(dto.getPlanId()).orElseThrow();
            payments = subscriptionPaymentRepo.findByOrderId(orderId);
            payments.setPaymentId(dto.getPaymentId());
            payments.setPayedOn(LocalDateTime.now());
            payments.setPayed(true);
            subscriptionPaymentRepo.save(payments);

            Subscription subscription= new Subscription();
            subscription.setSubscriptionPayments(payments);
            subscription.setStartFrom(LocalDateTime.now().withHour(0));
            subscription.setEndOn(LocalDateTime.now().plusMonths(6).withHour(23).withMinute(59));
            subscription.setPromoCode(null);
            subscription.setUserId(payments.getUserId());
            subscription.setSessionCount(plans.getNoOfSession());
            subscription.setPlans(plans);
            subscriptionRepo.save(subscription);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
}

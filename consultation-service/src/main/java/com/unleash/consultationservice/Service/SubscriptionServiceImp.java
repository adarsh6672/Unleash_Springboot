package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.Repository.PlanRepo;
import com.unleash.consultationservice.Service.serviceInterface.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubscriptionServiceImp implements SubscriptionService {

    @Autowired
    private PlanRepo planRepo;


    @Override
    public List findAllPlans(){
        return planRepo.findByIsHiddenFalse();
    }
}

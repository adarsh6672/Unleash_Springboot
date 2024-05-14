package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.Service.serviceInterface.SessionService;
import com.unleash.consultationservice.Service.serviceInterface.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plan/public")
public class PublicController {

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/get-all-plans")
    public ResponseEntity<?> getAllPlans(){
        try{
            List list = subscriptionService.findAllPlans();
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getfeedback/{counselorId}/{pageNo}")
    public ResponseEntity<?> getFeedback(@PathVariable("counselorId")int counselorId ,
                                         @PathVariable ("pageNo")int pageNo){
        return sessionService.getfeedbackofCounselor(counselorId,pageNo);
    }
}

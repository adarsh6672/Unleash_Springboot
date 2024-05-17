package com.unleash.consultationservice.controller;


import com.unleash.consultationservice.Service.serviceInterface.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultation/counselor")
public class CounselorPaymentController {

    @Autowired
    private CounselorService counselorService;

    @GetMapping("/get-account-details")
    public ResponseEntity<?> getAccountDetails(@RequestHeader("userId") int userId){
        return counselorService.getAccountDetails(userId);
    }

    @GetMapping("/get-dashboard-data")
    public ResponseEntity<?> getDashboardData(@RequestHeader("userId")int userId){
        return ResponseEntity.ok().body(counselorService.getDashboardData(userId));
    }

}

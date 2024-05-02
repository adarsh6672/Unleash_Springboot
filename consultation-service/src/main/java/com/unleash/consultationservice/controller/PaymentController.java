package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.DTO.CreateContactDto;
import com.unleash.consultationservice.Service.serviceInterface.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@RestController
@RequestMapping("/consultation/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping("/create-contact")
    public ResponseEntity<?>createContact(@RequestBody CreateContactDto dto){
        try {
            return paymentService.createContact(dto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Some Error Occurred Please Try Again");
        }
    }

    @GetMapping("/get-all-pendings")
    ResponseEntity<?>getAllPendingPayments (){
        return paymentService.getAllPendingPayments();
    }

    @PostMapping("/process/{id}")
    ResponseEntity<?> processPayment(@PathVariable ("id") int id){
        try {
            return paymentService.procesPayment(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }


    }

}


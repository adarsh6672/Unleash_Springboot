package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.DTO.CreateContactDto;
import com.unleash.consultationservice.Service.PaymentServiceImp;
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
import java.util.List;

@RestController
@RequestMapping("/consultation/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentServiceImp paymentServiceImp;


    @PostMapping("/create-contact")
    public ResponseEntity<?>createContact(@RequestBody CreateContactDto dto){
        try {
            return paymentService.createContact(dto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Some Error Occurred Please Try Again");
        }
    }

    @GetMapping("/get-lastweek-pendings/{pageNo}")
    public ResponseEntity<?>getAllPendingPayments (@PathVariable ("pageNo") int pageNo){
        return paymentService.getLastWeekPayments(pageNo);
    }

    @PostMapping("/process/{id}")
    public ResponseEntity<?> processPayment(@PathVariable ("id") int id){
        try {
            return paymentService.procesPayment(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }


    }

    @PostMapping("/process/selected")
    public ResponseEntity<?> processSelected(@RequestBody List<Integer> selectedIds){
        try{
            return paymentService.processPaymentOfSelected(selectedIds);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/counselor/get-transaction")
    public ResponseEntity<?> getMyTransactions(@RequestHeader("userId") int userId){
        return paymentService.getMyTransactions(userId);
    }

    @GetMapping("/admin/get-alltransaction/{pageNo}")
    public ResponseEntity<?> getAllTransactions(@PathVariable ("pageNo") int pageNo){
        return paymentService.getAllTransactionsPage(pageNo);
    }

    @GetMapping("/force-weekly-calculation")
    public ResponseEntity<?>createWeeklyCalculation(){
        paymentServiceImp.runWeeklyPaymentCalculation();
        return ResponseEntity.ok().body("generated payment list");
    }

}


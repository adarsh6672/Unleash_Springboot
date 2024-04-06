package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.Interface.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlotController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return userClient.getSelectionData();
    }
}

package com.unleash.userservice.controller;

import com.unleash.userservice.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/get-time-slots")
    public ResponseEntity<?> getTimeSlots(@RequestParam String date, @RequestParam int counselorId){
        return ResponseEntity.ok().body(userServiceImp.getAvilability(counselorId,date));
    }
}

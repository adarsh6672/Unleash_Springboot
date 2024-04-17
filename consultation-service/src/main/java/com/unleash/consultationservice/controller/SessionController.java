package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.Service.serviceInterface.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultation/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/book-slot")
    public ResponseEntity<?> bookSlot(@RequestHeader ("userId") int userId,
                                      @RequestParam int slotId){
        return sessionService.bookSession(userId , slotId);
    }

    @GetMapping("/get-dashboard-data")
    public ResponseEntity<?> getDashboardData(@RequestHeader("userId") int userId){
        return sessionService.getDashBoardData(userId);
    }

    @GetMapping("/get-allbookings")
    public ResponseEntity<?>getAllBooking(@RequestHeader ("userId") int userId){
        return sessionService.findAllBookingsByUser(userId);
    }
}

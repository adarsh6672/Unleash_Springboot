package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Service.serviceInterface.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultation/counselor")
public class SlotController {

    @Autowired
    private UserClient userClient;



    @Autowired
    CounselorService counselorService;

    @PostMapping("/schedule-time-slot")
    public ResponseEntity<?> scheduleTimeSlot(@RequestBody List<String> list,
                                              @RequestHeader ("username") String userName){
        System.out.println(userName);
        return ResponseEntity.ok().body(counselorService.setSlot(list,userName));
    }

    @GetMapping("/get-my-slots")
    public ResponseEntity<?> getMySlots(@RequestParam String date,
                                        @RequestHeader ("username") String userName){
        return ResponseEntity.ok().body(counselorService.findSlotmyslots(date,userName));
    }


    @GetMapping("/get-available-counselors")
    public ResponseEntity<?> findAvailableCounselors(){
        return ResponseEntity.ok().body(counselorService.findAvilableCounselors());
    }


    @GetMapping("/get-time-slots")
    public ResponseEntity<?> getTimeSlots(@RequestParam String date, @RequestParam int counselorId){
        return ResponseEntity.ok().body(counselorService.getAvilability(counselorId,date));
    }

    @DeleteMapping("/remove-slots")
    public ResponseEntity<?> romoveSloteOfDate(@RequestParam String date,
                                               @RequestHeader ("userId") int userId){

        return ResponseEntity.ok().body(counselorService.removeSlotOnDate(userId,date));
    }

    @DeleteMapping("/remove-slot")
    public ResponseEntity<?>removeSingleSlot(@RequestParam String date,
                                             @RequestHeader ("userId") int userId){
        return ResponseEntity.ok().body(counselorService.removeSingleSlot(userId , date));
    }


}

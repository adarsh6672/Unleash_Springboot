package com.unleash.userservice.controller;

import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/fetchpatients")
    public ResponseEntity<?> getPatients(){
        return ResponseEntity.ok().body(adminService.findPatients());
    }

    @GetMapping("/fetchcounsellors")
    public ResponseEntity<?> getCounsellors(){
        return ResponseEntity.ok().body(adminService.findAllCounselors());
    }

    @GetMapping("/fetchnewrequests")
    public ResponseEntity<?> getNewRequests(){
        return ResponseEntity.ok().body(adminService.findCounselorRequsets());
    }

    @GetMapping("/profileverification/{id}")
    public ResponseEntity<?> profileVerification(@PathVariable ("id") int id){
        return adminService.CounsellorProfile(id);
    }

    @GetMapping("/profilephoto/{id}")
    public ResponseEntity<?> profilePhoto(@PathVariable ("id") int id){
        return adminService.counselorImage(id);
    }

    @PutMapping("/blockuser/{id}")
    public ResponseEntity<?>blockUser(@PathVariable ("id") int id){
        if(adminService.blockUser(id)){
            return ResponseEntity.ok().body("sucussfully Blocked user");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/unblockuser/{id}")
    public ResponseEntity<?> unBlockUser(@PathVariable ("id") int id){
        if(adminService.unBlockUser(id)){
            return ResponseEntity.ok().body("successfully Unblocked User");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("verification/requests")
    public ResponseEntity<?> verificationRequests(){
        return ResponseEntity.ok().body("");
    }

    @PutMapping("vefify/counselor/{id}")
    public ResponseEntity<?> verifyCounselor(@PathVariable ("id") int id){
        return ResponseEntity.ok().body(adminService.verifyCounselor(id));
    }

}

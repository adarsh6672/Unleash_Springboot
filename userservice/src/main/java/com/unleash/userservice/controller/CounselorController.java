package com.unleash.userservice.controller;

import com.unleash.userservice.DTO.SelectionResponse;
import com.unleash.userservice.DTO.VerificationDataDto;
import com.unleash.userservice.Service.services.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/counselor")
public class CounselorController {

    private final CounselorService counselorService;

    @Autowired
    public CounselorController(CounselorService counselorService) {
        this.counselorService = counselorService;
    }


    @PostMapping(value = "/documentupload" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> counselorVerification(@RequestPart("qualification") MultipartFile qualification,
                                                   @RequestPart("experience") MultipartFile experience,
                                                   @RequestPart("profilePic") MultipartFile profilePic,
                                                   @RequestHeader("Authorization") String token) throws IOException {
        if(counselorService.saveDocuments(qualification,experience,profilePic,token)){
            return ResponseEntity.ok().body("uploaded successfully");
        }
        return ResponseEntity.internalServerError().body("upload failed");
    }

    @PostMapping("/dataupload")
    public ResponseEntity<?> dataVerification(@RequestBody VerificationDataDto dto,
                                              @RequestHeader ("Authorization") String headerToken){

        if(counselorService.uploadData(dto,headerToken)){
            return ResponseEntity.ok("uploaded success");
        }
        return ResponseEntity.badRequest().body("file not uploaded");
    }

    @GetMapping("/verificationstatus")
    public ResponseEntity<?> verificationStatus(@RequestHeader("Authorization") String headerToken){
        return counselorService.isProfileVerified(headerToken);
    }

    @GetMapping("/getselectiondata")
    public ResponseEntity<SelectionResponse> getSelectionData(){
        return ResponseEntity.ok().body(counselorService.getSelectionData());
    }

    @GetMapping("/get-profile-data")
    public ResponseEntity<?> getProfileData(@RequestHeader("Authorization") String token){
        return counselorService.CounsellorProfile(token);
    }


    // ------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------

    @PostMapping("/schedule-time-slot")
    public ResponseEntity<?> scheduleTimeSlot(@RequestBody List<String> list,
                                              @RequestHeader ("Authorization") String headerToken){
        return ResponseEntity.ok().body(counselorService.setSlot(list,headerToken));
    }

    @GetMapping("/get-my-slots")
    public ResponseEntity<?> getMySlots(@RequestParam String date,
                                        @RequestHeader ("Authorization") String headerToken){
        return ResponseEntity.ok().body(counselorService.findSlotmyslots(date,headerToken));
    }

}

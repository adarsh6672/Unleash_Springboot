package com.unleash.userservice.controller;

import com.unleash.userservice.DTO.SelectionResponse;
import com.unleash.userservice.DTO.VerificationDataDto;
import com.unleash.userservice.Service.JwtServiceImp;
import com.unleash.userservice.Service.UserServiceImp;
import com.unleash.userservice.Service.services.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/counselor")
public class CounselorController {

    private final CounselorService counselorService;
    @Autowired
    private JwtServiceImp jwtServiceImp;

    @Autowired
    private UserServiceImp userServiceImp;

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



    @PostMapping("/profile-data-updation")
    public ResponseEntity<?> profileDataUpdation(@RequestBody VerificationDataDto dto,
                                              @RequestHeader ("Authorization") String headerToken){

        if(counselorService.updateProfileData(dto,headerToken)){
            return ResponseEntity.ok("uploaded success");
        }
        return ResponseEntity.badRequest().body("file not uploaded");
    }

    @PostMapping(value = "/documentUpdation" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> DocumentUpdation(@RequestPart(value = "qualification",required = false) MultipartFile qualification,
                                                   @RequestPart(value = "experience",required = false) MultipartFile experience,
                                                   @RequestHeader("Authorization") String token) throws IOException {
        if(counselorService.updateDocuments(qualification,experience,token)){
            return ResponseEntity.ok().body("uploaded successfully");
        }
        return ResponseEntity.internalServerError().body("upload failed");
    }

   @GetMapping("/get-username")
    public String findUsername(@RequestParam int userId){
        return counselorService.findUserName(userId);
   }

   @GetMapping("/get-all-counselors")
    public List findAllCounselors(){
        return userServiceImp.findAllCounselors();
   }



}

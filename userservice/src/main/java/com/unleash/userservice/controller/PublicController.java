package com.unleash.userservice.controller;

import com.unleash.userservice.Service.CloudinaryServiceImp;
import com.unleash.userservice.Service.PublicServiceImp;
import com.unleash.userservice.Service.services.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final CounselorService counselorService;
    @Autowired
    private CloudinaryServiceImp cloudinaryServiceImp;

    @Autowired
    private PublicServiceImp publicServiceImp;

    @Autowired
    public PublicController(CounselorService counselorService) {
        this.counselorService = counselorService;
    }

    @GetMapping("/selection-data")
    public ResponseEntity<?> getSelectionData(){
        return ResponseEntity.ok().body(counselorService.getSelectionData());
    }

    @GetMapping("/get-available-counsellors")
    public ResponseEntity<?> getAllAvailableCounsellors(){
        return ResponseEntity.ok().body(publicServiceImp.findAvilableCounselors());
    }

    @PostMapping("/post")
    public ResponseEntity<?> test(@RequestBody MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(cloudinaryServiceImp.upload(file));
    }

}

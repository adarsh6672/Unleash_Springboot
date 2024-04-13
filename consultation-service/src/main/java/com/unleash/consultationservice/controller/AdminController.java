package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.DTO.PlanDto;
import com.unleash.consultationservice.Service.CloudinaryService;
import com.unleash.consultationservice.Service.serviceInterface.AdminServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consultation/admin")
public class AdminController {

    @Autowired
    private AdminServic adminServic;



    @PostMapping(value = "/add-plan" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPlan(@RequestParam Map<String, Object> data,
                                     @RequestParam MultipartFile file){
        try{
            adminServic.addPlan(data,file);
            return ResponseEntity.ok().body("added");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();

        }
    }

    @GetMapping("get-all-plans")
    public ResponseEntity<?> getAllPlans(){
        try{
            List list= adminServic.findAllPlans();
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}

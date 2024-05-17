package com.unleash.consultationservice.controller;

import com.unleash.consultationservice.DTO.PromocodeDTO;
import com.unleash.consultationservice.Service.serviceInterface.AdminServic;
import com.unleash.consultationservice.Service.serviceInterface.PromocodeService;
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

    @Autowired
    private PromocodeService promocodeService;


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

    @GetMapping("/get-all-plans")
    public ResponseEntity<?> getAllPlans(){
        try{
            List list= adminServic.findAllPlans();
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping ("/get-all-bookings/{pageNo}")
    public ResponseEntity<?> getAllBookings(@PathVariable ("pageNo") int pageNo){
        return adminServic.getAllBookingDetails(pageNo);
    }

    @PostMapping ("/add-promocode")
    public ResponseEntity<?> addPromocode(@RequestBody PromocodeDTO promocode){
        return promocodeService.addPromocode(promocode);
    }

    @GetMapping("/getAllPromocode/{pageNo}")
    public ResponseEntity<?> getAllPromocodes(@PathVariable ("pageNo") int pageNo){
        return promocodeService.getAllPromocode(pageNo);
    }

    @GetMapping("/get-dashboard-data")
    public ResponseEntity<?> getDashboardData(){
        return ResponseEntity.ok().body(adminServic.getDashboardData());
    }

    @GetMapping("/subscription-data/{wise}")
    public ResponseEntity<?> getSubscriptionChartData(@PathVariable("wise")String wise){
        return ResponseEntity.ok().body(adminServic.getSubscriptionChartData(wise));
    }

}

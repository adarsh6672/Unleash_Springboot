package com.unleash.userservice.Interface;

import com.unleash.userservice.DTO.AvilabilityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "consultation-service" )
public interface ConsultationClient {

    @GetMapping("/consultation/counselor/get-available-counselors")
    public ResponseEntity<List<AvilabilityDto>> findAvailableCounselors();
}

package com.unleash.consultationservice.Interface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service" ,path = "/public")
public interface UserClient {

    @GetMapping("/selection-data")
     ResponseEntity<?> getSelectionData();
}

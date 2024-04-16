package com.unleash.consultationservice.Interface;

import com.unleash.consultationservice.DTO.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service" )
public interface UserClient {

    @GetMapping("/public/selection-data")
     ResponseEntity<?> getSelectionData();


    @GetMapping("/public/get-user")
    public ResponseEntity<UserDto> getUserWithUserName(@RequestParam("username") String userName);

    @GetMapping("/user/counselor/test")
    public ResponseEntity<String> test();

    @GetMapping("/user/counselor/get-username")
    public String findUsername(@RequestParam int userId);

}

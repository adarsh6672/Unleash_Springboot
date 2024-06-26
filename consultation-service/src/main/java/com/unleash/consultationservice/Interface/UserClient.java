package com.unleash.consultationservice.Interface;

import com.unleash.consultationservice.DTO.DashboardDTO;
import com.unleash.consultationservice.DTO.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/user/counselor/get-all-counselors")
    public List<UserDto> findAllCounselors();

    @GetMapping("/user/admin/fetchpatients")
    public ResponseEntity<List<UserDto>> getPatients();

    @GetMapping("/user/admin/get-dashboard-data")
    public ResponseEntity<DashboardDTO>getAdminDashboradData();

    @GetMapping("/public/get-user/{id}")
    public ResponseEntity<UserDto> getUserWithUserId(@PathVariable("id") int id);
}

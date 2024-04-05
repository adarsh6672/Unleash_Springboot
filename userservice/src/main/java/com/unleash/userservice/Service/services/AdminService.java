package com.unleash.userservice.Service.services;

import com.unleash.userservice.DTO.CouselorDataDto;
import com.unleash.userservice.DTO.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    List<UserDto> findPatients();

    List<UserDto> findAllCounselors();

    List<UserDto> findCounselorRequsets();

    ResponseEntity CounsellorProfile(int id);

    ResponseEntity<?> counselorImage(int id);

    boolean verifyCounselor(int id);

    boolean blockUser(int id);

    boolean unBlockUser(int id);
}

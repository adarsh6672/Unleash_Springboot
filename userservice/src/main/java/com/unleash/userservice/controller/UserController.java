package com.unleash.userservice.controller;

import com.unleash.userservice.DTO.ChangePasswordDTO;
import com.unleash.userservice.DTO.ProfileDataDTO;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/get-user-data")
    public ResponseEntity<?> getUserData(@RequestHeader ("Authorization") String token){
        return ResponseEntity.ok().body(userServiceImp.findUserData(token));

    }

    @PostMapping("/update-profile-photo")
    public ResponseEntity<?> updateProfilePhoto(@RequestPart("profilePic") MultipartFile profilePic,
                                               @RequestHeader ("userId") int userId){
        return userServiceImp.updateProfilePic(userId,profilePic);
    }

    @PutMapping("/update-profile-data")
    public ResponseEntity<?> updateProfileData(@RequestHeader ("userId") int userId,
                                               @RequestBody ProfileDataDTO profileDataDTO){
        return userServiceImp.updateProfileData(userId,profileDataDTO);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestHeader ("userId") int userId,
                                            @RequestBody ChangePasswordDTO changePasswordDTO){
        return userServiceImp.updatePassword(userId, changePasswordDTO);
    }
}

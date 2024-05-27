package com.unleash.userservice.controller;

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
    public ResponseEntity<?> updateProfileData(@RequestPart("profilePic") MultipartFile profilePic,
                                               @RequestHeader ("userId") int userId){
        return userServiceImp.updateProfilePic(userId,profilePic);
    }
}

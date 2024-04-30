package com.unleash.userservice.DTO;


import com.unleash.userservice.Model.User;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;

    private String role;

    private UserDto user;


    public AuthenticationResponse(String token , String role ,UserDto user) {
        this.token = token;
        this.role = role;
        this.user= user;

    }


}


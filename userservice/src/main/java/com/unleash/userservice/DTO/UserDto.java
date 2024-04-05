package com.unleash.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unleash.userservice.Model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto {

    private int id;
    private String username;

    private String fullname;

    private String phone;

    private String email;

    private String profilePic;

    @JsonIgnore
    private String password;

    private Role role;

    private LocalDateTime joinedOn;

    private boolean isBlocked;

    @JsonIgnore
    private String otp;

}

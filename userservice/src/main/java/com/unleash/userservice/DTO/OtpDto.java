package com.unleash.userservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpDto {

    private String email;
    private String otp;
    private String password;
}

package com.unleash.userservice.Service.services;

import com.unleash.userservice.DTO.OtpDto;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.User;

public interface OtpService {





    String generateOTP(User user);

    boolean validateOTP(OtpDto otpDto);

    String forgotPassword(String email);
}

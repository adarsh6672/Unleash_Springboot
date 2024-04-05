package com.unleash.userservice.Service.services;

import com.unleash.userservice.DTO.OtpDto;
import com.unleash.userservice.DTO.AuthenticationResponse;
import com.unleash.userservice.Model.User;

public interface AuthenticationService {




    AuthenticationResponse register(User request);

    AuthenticationResponse authenticate(User request);

    boolean isBlocked(User request);

    boolean isEmailExisting(String email);


    boolean updatePassword(OtpDto dto);
}

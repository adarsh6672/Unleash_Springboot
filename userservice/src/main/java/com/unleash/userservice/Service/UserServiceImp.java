package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImp {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto findUserData(String token){
        String  userName = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(userName).orElseThrow();
        UserDto dto= modelMapper.map(user,UserDto.class);
        return dto;
    }
}

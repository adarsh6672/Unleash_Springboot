package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.CounselorDateRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CounselorDateRepository counselorDateRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto findUserData(String token){
        String  userName = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(userName).orElseThrow();
        UserDto dto= modelMapper.map(user,UserDto.class);
        return dto;
    }


    public List<UserDto> findAllCounselors() {
        List<User> users = new ArrayList<>();
        List<CounselorData> list = counselorDateRepository.findByIsVerifiedTrue();
        for (CounselorData counselorData : list) {
            users.add(counselorData.getUser());
        }
        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;
    }
}

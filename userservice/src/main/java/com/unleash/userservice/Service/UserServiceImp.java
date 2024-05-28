package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.ChangePasswordDTO;
import com.unleash.userservice.DTO.ProfileDataDTO;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.CounselorDateRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private CloudinaryServiceImp cloudinaryServiceImp;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public ResponseEntity<?> updateProfilePic(int userId, MultipartFile profilePic) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            String uri = cloudinaryServiceImp.upload(profilePic);
            user.setProfilePic(uri);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateProfileData(int userId, ProfileDataDTO profileDataDTO) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            if(!profileDataDTO.getFullName().equals(user.getFullname())){
                user.setFullname(profileDataDTO.getFullName());
                System.out.println("name updated");
            }
            if(!profileDataDTO.getPhone().equals(user.getPhone())){
                user.setPhone(profileDataDTO.getPhone());
                System.out.println("phone updated");
            }
            userRepository.save(user);
            UserDto userDto = modelMapper.map(user,UserDto.class);
            return ResponseEntity.ok().body(userDto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> updatePassword(int userId, ChangePasswordDTO changePasswordDTO) {
        try{
            User user = userRepository.findById(userId).orElseThrow();
            if(passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.getPassword())){
                user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.ok().body("Password Changed Successfully");
            }else {
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("password changing failed");
    }
}

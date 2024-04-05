package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.CouselorDataDto;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.CounselorDateRepository;
import com.unleash.userservice.Reposetory.LanguageRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImp implements AdminService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    private final CounselorDateRepository counselorDateRepository;

    private final LanguageRepository languageRepository;

    @Autowired
    public AdminServiceImp(ModelMapper modelMapper, UserRepository userRepository, CounselorDateRepository counselorDateRepository, LanguageRepository languageRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.counselorDateRepository = counselorDateRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public List<UserDto> findPatients(){
       List<User> users= userRepository.findAllUsersWithUserRole();
        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public List<UserDto> findAllCounselors(){
        List<User> users = new ArrayList<>();
        List<CounselorData> list= counselorDateRepository.findByIsVerifiedTrue();
        for(CounselorData counselorData : list){
            users.add(counselorData.getUser());
        }


        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public List<UserDto> findCounselorRequsets (){
        List<User> users = new ArrayList<>();
        List<CounselorData> list= counselorDateRepository.findByIsVerifiedFalse();
        for(CounselorData counselorData : list){
            users.add(counselorData.getUser());
        }


        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public ResponseEntity<?> CounsellorProfile(int id){
        User user=userRepository.findById(id).orElseThrow();
        CounselorData data=counselorDateRepository.findByUser(user).orElseThrow();
        CouselorDataDto dto = new CouselorDataDto();
        modelMapper.map(user , dto);
        modelMapper.map(data,dto);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<?> counselorImage(int id){
        User user = userRepository.findById(id).orElseThrow();
        try {
            String image=user.getProfilePic();
            RandomAccessFile f = new RandomAccessFile(image, "r");
            byte[] b = new byte[(int)f.length()];
            f.readFully(b);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public boolean verifyCounselor(int id){
        User user= userRepository.findById(id).orElseThrow();
        CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();
        counselorData.setVerified(true);
        counselorDateRepository.save(counselorData);
        return true;
    }

    @Override
    public boolean blockUser(int id){
        try{
            User user=userRepository.findById(id).orElseThrow();
            user.setBlocked(true);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }

    }


    @Override
    public boolean unBlockUser(int id){
        try{
            User user=userRepository.findById(id).orElseThrow();
            user.setBlocked(false);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }

    }





}

package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.CounselorDTO;
import com.unleash.userservice.DTO.CouselorDataDto;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.CounselorAvilabilityRepository;
import com.unleash.userservice.Reposetory.CounselorDateRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicServiceImp {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CounselorDateRepository counselorDateRepository;

    @Autowired
    private CounselorAvilabilityRepository counselorAvilabilityRepository;

    public List findAvilableCounselors(){
        List<User> counselors= new ArrayList<>();
        List<CounselorData> counselorData= counselorDateRepository.findByIsVerifiedTrue();
        DateTimeFormatter formatter= DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime= LocalDateTime.parse(LocalDateTime.now().toString(),formatter);
        List<CounselorDTO> userDtos = counselorData.stream()
                .map(user -> modelMapper.map(user, CounselorDTO.class))
                .collect(Collectors.toList());

        for (CounselorDTO dto : userDtos){
            try{
                User user= userRepository.findById(dto.getUser().getId()).orElseThrow();
                dto.setNextAvailable(counselorAvilabilityRepository.findFirstByUserAndSlotAfterAndIsBookedFalseOrderBySlotAsc(user, localDateTime).orElseThrow());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return userDtos;
    }
}

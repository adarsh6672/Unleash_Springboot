package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.AvilabilityDto;
import com.unleash.userservice.DTO.CounselorDTO;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.CounselorDateRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Interface.ConsultationClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private ConsultationClient consultationClient;


    public List findAvilableCounselors(){
        List<User> counselors= new ArrayList<>();
        List<CounselorData> counselorData= counselorDateRepository.findByIsVerifiedTrue();
        List<CounselorDTO> userDtos = counselorData.stream()
                .map(user -> modelMapper.map(user, CounselorDTO.class))
                .collect(Collectors.toList());
        List<AvilabilityDto> avilabilityDtos=null;
        try {
            avilabilityDtos=consultationClient.findAvailableCounselors().getBody();
        }catch (Exception e){
            System.out.println("server error");
        }

        for (CounselorDTO dto : userDtos){
                AvilabilityDto next =avilabilityDtos.stream()
                        .filter(data-> data.getUserId() == dto.getUser().getId()).findFirst().orElse(null);
                if(next!=null){
                    dto.setNextAvailable(next.getSlot().toString());
                }else {
                    dto.setNextAvailable(null);
                }


        }
        return userDtos;
    }

    public ResponseEntity<?> getCounselorProfileById(int counsId){
        try {
            User counselor = userRepository.findById(counsId).orElseThrow();
            CounselorData data = counselorDateRepository.findByUser(counselor).orElseThrow();
            CounselorDTO counselorDTO = modelMapper.map(data,CounselorDTO.class);
            List<AvilabilityDto> avilabilityDtos= avilabilityDtos=consultationClient.findAvailableCounselors().getBody();

                AvilabilityDto next =avilabilityDtos.stream()
                        .filter(item-> item.getUserId() == counsId).findFirst().orElse(null);
                if(next!=null){
                    counselorDTO.setNextAvailable(next.getSlot().toString());
                }else {
                    counselorDTO.setNextAvailable(null);
                }
                return ResponseEntity.ok().body(counselorDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }


    public ResponseEntity getUser(String userName){
        System.out.println(userName);
        try{
            User user = userRepository.findByUsername(userName).orElseThrow();
            UserDto dto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok().body(dto);

        }catch (Exception e){
            System.out.println("user not found");
            return ResponseEntity.notFound().build();
        }


    }

    public ResponseEntity<?> getUserById(int id) {
        try {
            User user= userRepository.findById(id).orElseThrow();
            UserDto dto = modelMapper.map(user,UserDto.class);
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}

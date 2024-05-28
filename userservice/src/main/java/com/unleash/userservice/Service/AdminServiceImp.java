package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.CouselorDataDto;
import com.unleash.userservice.DTO.DashboardDTO;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.*;
import com.unleash.userservice.Reposetory.CounselorDateRepository;
import com.unleash.userservice.Reposetory.CounselorUpdationRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImp implements AdminService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    private final CounselorDateRepository counselorDateRepository;

    private final LanguageRepository languageRepository;

    private final CounselorUpdationRepository counselorUpdationRepository;

    @Autowired
    public AdminServiceImp(ModelMapper modelMapper, UserRepository userRepository, CounselorDateRepository counselorDateRepository, LanguageRepository languageRepository, CounselorUpdationRepository counselorUpdationRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.counselorDateRepository = counselorDateRepository;
        this.languageRepository = languageRepository;
        this.counselorUpdationRepository = counselorUpdationRepository;
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

    @Override
    public List<ConselorUpdations> findCounselorUpdations(){

        List<ConselorUpdations> users=counselorUpdationRepository.findAll();
        return users;

    }

    @Override
    public boolean approveUpdate(int id){
        try{
            ConselorUpdations updations= counselorUpdationRepository.findById(id).orElseThrow();
            User user= updations.getUser();
            CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();

            user.setFullname(updations.getFullname());


            counselorData.setSpecializations(counselorData.getSpecializations());
            if(updations.getExperienceProof()!=null){
                counselorData.setYoe(updations.getYoe());
                counselorData.setExperienceProof(updations.getExperienceProof());
            }
            if(updations.getQualificationProof()!=null){
                counselorData.setQualificationProof(updations.getQualificationProof());
                counselorData.setQualification(updations.getQualification());
            }


            Set<Language> lang1 = counselorData.getLanguages();
            Set<Language> lang2 = updations.getLanguages();
            lang1.addAll(lang2);
            counselorData.setLanguages(lang1);


            Set<Specialization> spe1 = counselorData.getSpecializations();
            Set<Specialization> spe2 = updations.getSpecializations();
            spe1.addAll(spe2);
            counselorData.setSpecializations(spe1);

            counselorDateRepository.save(counselorData);
            userRepository.save(user);
            counselorUpdationRepository.deleteById(id);
            System.out.println("its done here ================================================");
            return true;
        }catch (Exception e){
            System.out.println("exception==================================================");
            e.printStackTrace();
            return false;

        }

    }

    @Override
    public DashboardDTO getDashboardData() {
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setTotalCounselors(findAllCounselors().size());
        dashboardDTO.setTotalPatients(findPatients().size());
        return dashboardDTO;
    }


}

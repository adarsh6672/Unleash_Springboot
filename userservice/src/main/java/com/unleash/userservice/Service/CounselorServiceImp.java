package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.SelectionResponse;
import com.unleash.userservice.DTO.VerificationDataDto;
import com.unleash.userservice.Model.*;
import com.unleash.userservice.Reposetory.*;
import com.unleash.userservice.Service.services.CounselorService;
import com.unleash.userservice.Service.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class CounselorServiceImp implements CounselorService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CloudinaryServiceImp cloudinaryServiceImp;
    private final CounselorDateRepository counselorDateRepository;
    private final QualificationRepository qualificationRepository;
    private final LanguageRepository languageRepository;
    private final GenderRepository genderRepository;
    private final SpecializationRepository specializationRepository;
    private final CounselorAvilabilityRepository counselorAvilabilityRepository;



    private final String PATH = "/home/adarsh/BROTOTYPE/Unleash_App/userservice/src/main/resources/static/CounselorDocuments";

    @Autowired
    public CounselorServiceImp(JwtService jwtService, UserRepository userRepository, CloudinaryServiceImp cloudinaryServiceImp, CounselorDateRepository counselorDateRepository, QualificationRepository qualificationRepository, LanguageRepository languageRepository, GenderRepository genderRepository, SpecializationRepository specializationRepository, CounselorAvilabilityRepository counselorAvilabilityRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cloudinaryServiceImp = cloudinaryServiceImp;

        this.counselorDateRepository = counselorDateRepository;
        this.qualificationRepository = qualificationRepository;
        this.languageRepository = languageRepository;
        this.genderRepository = genderRepository;
        this.specializationRepository = specializationRepository;
        this.counselorAvilabilityRepository = counselorAvilabilityRepository;
    }

    @Override
    public boolean saveDocuments(MultipartFile qualification, MultipartFile experience, MultipartFile profilePic, String token) throws IOException {
        String  userName = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(userName).orElseThrow();
        CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();
        try {
            String qualipath= cloudinaryServiceImp.upload(qualification);
            String expPath=cloudinaryServiceImp.upload(experience);
            String photo = cloudinaryServiceImp.upload(profilePic);
            counselorData.setExperienceProof(expPath);
            counselorData.setQualificationProof(qualipath);
            user.setProfilePic(photo);
            counselorDateRepository.save(counselorData);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public String uploadFile(String type, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        file.transferTo(new File(PATH+"/"+type+"/"+fileName));
        return PATH+"/"+type+"/"+fileName;
    }

    @Override
    public boolean uploadData(VerificationDataDto data, String token){
        try {
            String  userName = jwtService.extractUsername(token.substring(7));
            User user = userRepository.findByUsername(userName).orElseThrow();
            CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();
            counselorData.setQualification(qualificationRepository.findById(data.getQualificationId()).orElseThrow());
          /*  counselorData.setLanguages((Set<Language>) languageRepository.findById(data.getLanguageId()).orElseThrow());*/
            /*counselorData.setSpecialization(specializationRepository.getReferenceById(data.getSpecializationId()));*/
            counselorData.setUploadedOn(LocalDateTime.now());
            counselorData.setYoe(data.getYoe());
            counselorData.setGender(genderRepository.findById(data.getGenderId()).orElseThrow());

            List<Integer> specializationIds = data.getSpecializations();
            Set<Specialization> sp = counselorData.getSpecializations();
            for(Integer spl : specializationIds){
                sp.add(specializationRepository.findById(spl).orElseThrow());
            }
            counselorData.setSpecializations(sp);

            List<Integer> langIds = data.getLanguages();
            Set<Language> la= counselorData.getLanguages();
            for(Integer language : langIds){
                la.add(languageRepository.findById(language).orElseThrow());
            }
            counselorData.setLanguages(la);
            counselorDateRepository.save(counselorData);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public SelectionResponse getSelectionData(){
        SelectionResponse selectionResponse= new SelectionResponse();
        selectionResponse.setLanguages(languageRepository.findAll());
        selectionResponse.setQualifications(qualificationRepository.findAll());
        selectionResponse.setSpecializations(specializationRepository.findAll());
        return selectionResponse;
    }


    @Override
    public ResponseEntity<?> isProfileVerified(String token){
        User user= userRepository.findByUsername(jwtService.extractUsername(token.substring(7))).orElseThrow();
        CounselorData data=counselorDateRepository.findByUser(user).orElseThrow();
        if(data.getUploadedOn()!=null){
            if(data.isVerified()){
                return ResponseEntity.ok().body("verified");
            }else {
                return ResponseEntity.ok().body("uploaded");
            }
        }
        return ResponseEntity.ok().body(null);
    }

    //=============================================================================================================
    //=============================================================================================================

    @Override
    public boolean setSlot(List<String> list, String token){
        DateTimeFormatter formatter= DateTimeFormatter.ISO_DATE_TIME;

        String  userName = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(userName).orElseThrow();

        for (String slot : list){
            CounselorAvilability counselorAvilability= new CounselorAvilability();
            LocalDateTime localDateTime = LocalDateTime.parse(slot, formatter);
            counselorAvilability.setUser(user);
            counselorAvilability.setSlot(localDateTime);
            counselorAvilability.setBooked(false);
            counselorAvilabilityRepository.save(counselorAvilability);
        }
        return true;
    }

    @Override
    public List findSlotmyslots(String token){
        String  userName = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(userName).orElseThrow();
        List<CounselorAvilability> list = counselorAvilabilityRepository.findByUserAndSlotGreaterThanEqual(user, LocalDateTime.now());
        return list;
    }


}

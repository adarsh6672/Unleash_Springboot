package com.unleash.userservice.Service.services;

import com.unleash.userservice.DTO.SelectionResponse;
import com.unleash.userservice.DTO.VerificationDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CounselorService {
    boolean saveDocuments(MultipartFile qualification, MultipartFile experience, MultipartFile profilePic, String token) throws IOException;

    String uploadFile(String type, MultipartFile file) throws IOException;

    boolean uploadData(VerificationDataDto data, String token);

    SelectionResponse getSelectionData();

    ResponseEntity isProfileVerified(String token);

    ResponseEntity<?> CounsellorProfile(String token);


    boolean updateProfileData(VerificationDataDto data, String token);

    boolean updateDocuments(MultipartFile qualification, MultipartFile experience, String token) throws IOException;

    String findUserName(int userId);
}

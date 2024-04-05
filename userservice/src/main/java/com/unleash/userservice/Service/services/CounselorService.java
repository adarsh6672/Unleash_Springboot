package com.unleash.userservice.Service.services;

import com.unleash.userservice.DTO.SelectionResponse;
import com.unleash.userservice.DTO.VerificationDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CounselorService {
    boolean saveDocuments(MultipartFile qualification, MultipartFile experience, MultipartFile profilePic, String token) throws IOException;

    String uploadFile(String type, MultipartFile file) throws IOException;

    boolean uploadData(VerificationDataDto data, String token);

    SelectionResponse getSelectionData();

    ResponseEntity isProfileVerified(String token);
}

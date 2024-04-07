package com.unleash.userservice.DTO;

import com.unleash.userservice.Model.Language;
import com.unleash.userservice.Model.Specialization;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class VerificationDataDto {

    private int qualificationId ;
    private int genderId;
    private List<Integer> languages;
    private int yoe;
    private  List<Integer> specializations;
    private String fullname;




}

package com.unleash.userservice.DTO;

import com.unleash.userservice.Model.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CounselorDTO {

    private int id;

    private UserDto user;


    private Qualification qualification;


    private Set<Language> languages = new HashSet<>();


    private Set<Specialization> specializations = new HashSet<>();



    private Gender gender;

    private int yoe;

    private String nextAvailable;


}

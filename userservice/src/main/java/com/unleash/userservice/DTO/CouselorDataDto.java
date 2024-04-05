package com.unleash.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unleash.userservice.Model.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CouselorDataDto {
    private int id;
    private String username;

    private String fullname;

    private String phone;

    private String email;


    private Role role;

    private LocalDateTime joinedOn;


    private Qualification qualification;


    private boolean isVerified;


    private Set<Language> languages = new HashSet<>();


    private Set<Specialization> specializations= new HashSet<>();

    private String qualificationProof;

    private String experienceProof;


    private Gender gender;

    private int yoe;


    private LocalDateTime uploadedOn;

    private String profilePic;

}

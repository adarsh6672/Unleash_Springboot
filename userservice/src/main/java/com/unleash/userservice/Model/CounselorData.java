package com.unleash.userservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class CounselorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;


    private boolean isVerified;

    @ManyToMany()
    @JoinTable(
            name = "user_language",
            joinColumns = @JoinColumn(name = "counselor_data_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")

    )
    private Set<Language> languages = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "counselor_specializations",
            joinColumns = @JoinColumn(name = "counselor_data_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations = new HashSet<>();



    private String qualificationProof;

    private String experienceProof;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    private int yoe;


    private LocalDateTime uploadedOn;

    public CounselorData(User user) {
        this.user = user;
        this.isVerified = false;
    }


}

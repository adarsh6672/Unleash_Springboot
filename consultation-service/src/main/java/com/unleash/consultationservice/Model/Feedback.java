package com.unleash.consultationservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int patientId;

    private int counselorId;

    @OneToOne
    @JoinColumn(name = "session_id")
    private SessionBooking sessionBooking;

    private int rating;

    @Column(columnDefinition="TEXT")
    private String feedback;

    private LocalDateTime timeStamp;
}

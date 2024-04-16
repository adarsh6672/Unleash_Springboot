package com.unleash.consultationservice.Model;

import com.unleash.consultationservice.Model.Util.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class SessionBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "availability_id")
    private CounselorAvilability avilability;

    private int patientId;

    private LocalDateTime bookingTime;

    private String note;

    private Status status;


}

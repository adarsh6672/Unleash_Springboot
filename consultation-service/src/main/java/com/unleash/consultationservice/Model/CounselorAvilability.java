package com.unleash.consultationservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class CounselorAvilability {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;

        private int userId;

        private LocalDateTime slot;

        private boolean isBooked;

}

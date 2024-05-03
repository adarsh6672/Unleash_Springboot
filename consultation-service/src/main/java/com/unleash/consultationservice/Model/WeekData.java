package com.unleash.consultationservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class WeekData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    LocalDateTime startDate ;


    LocalDateTime endDate;
}

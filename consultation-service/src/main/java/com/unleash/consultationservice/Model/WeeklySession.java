package com.unleash.consultationservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class WeeklySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private String  userName;

    private int count;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "week_id")
    private WeekData week;

    private boolean isPayed;

    @OneToOne
    @JoinColumn(name = "transactionId")
    private CounselorTransactions transactions;

}

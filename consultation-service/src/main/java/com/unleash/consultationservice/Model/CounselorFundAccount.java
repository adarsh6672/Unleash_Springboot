package com.unleash.consultationservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CounselorFundAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    int userId;

    String fundAccount;

    String ifcCode;

    String accountNo;

    String contactId;
}

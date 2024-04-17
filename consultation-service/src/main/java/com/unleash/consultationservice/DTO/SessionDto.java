package com.unleash.consultationservice.DTO;

import com.unleash.consultationservice.Model.SessionBooking;
import com.unleash.consultationservice.Model.Subscription;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SessionDto {

    private SessionBooking sessionBooking;


    private String counselorName;

    private int counselorId;
}

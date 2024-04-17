package com.unleash.consultationservice.DTO;

import com.unleash.consultationservice.Model.Plans;
import com.unleash.consultationservice.Model.SessionBooking;
import com.unleash.consultationservice.Model.Subscription;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDashboardResponseDto {
    private Plans plan;

    private SessionBooking sessionBooking;

    private Subscription subscription;

    private String counselorName;

    private int counselorId;
}

package com.unleash.consultationservice.DTO;

import com.unleash.consultationservice.Model.SessionBooking;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CounselorSessionDto {

    SessionBooking sessionBooking;
    UserDto userDto;

}

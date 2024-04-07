package com.unleash.userservice.Service;

import com.unleash.userservice.Model.CounselorAvilability;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.CounselorAvilabilityRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImp {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CounselorAvilabilityRepository counselorAvilabilityRepository;

    public List<CounselorAvilability> getAvilability(int id , String date){

        User user = userRepository.findById(id).orElseThrow();
        DateTimeFormatter formatter= DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter).plusDays(1).withHour(0);
        List<CounselorAvilability> list = counselorAvilabilityRepository.findByUserAndSlotBetween(user,localDateTime,localDateTime.withHour(23));
        return list;
    }
}

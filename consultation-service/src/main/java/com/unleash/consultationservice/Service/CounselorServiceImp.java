package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.DTO.UserDto;
import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Model.CounselorAvilability;
import com.unleash.consultationservice.Repository.CounselorAvailabilityRepo;
import com.unleash.consultationservice.Service.serviceInterface.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CounselorServiceImp implements CounselorService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private CounselorAvailabilityRepo counselorAvailabilityRepo;

    @Override
    public boolean setSlot(List<String> list, String username){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");

         ResponseEntity<UserDto> userResponse = userClient.getUserWithUserName(username);
         UserDto userDto = userResponse.getBody();

        for (String slot : list){
            CounselorAvilability counselorAvilability= new CounselorAvilability();
            LocalDateTime dateTime = LocalDateTime.parse(slot, formatter);
            counselorAvilability.setUserId(userDto.getId());
            counselorAvilability.setSlot(dateTime);
            counselorAvilability.setBooked(false);
            counselorAvailabilityRepo.save(counselorAvilability);
        }
        return true;
    }

    @Override
    public List findSlotmyslots(String date, String username){
        ResponseEntity<UserDto> userResponse = userClient.getUserWithUserName(username);
        UserDto userDto = userResponse.getBody();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        System.out.println(dateTime+"====================================");
        List<CounselorAvilability> list = counselorAvailabilityRepo.findByUserIdAndSlotBetween(userDto.getId(),dateTime,dateTime.withHour(23));
        return list;
    }

    @Override
    public List findAvilableCounselors(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
        LocalDateTime currentTime= LocalDateTime.now();

        return counselorAvailabilityRepo.findNextAvailableSlots(currentTime);
    }

    @Override
    public List<CounselorAvilability> getAvilability(int counselorId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
        LocalDateTime startDate= LocalDateTime.parse(date,formatter);
        return counselorAvailabilityRepo.findByUserIdAndSlotBetween(counselorId,startDate,startDate.withHour(23));
    }
}

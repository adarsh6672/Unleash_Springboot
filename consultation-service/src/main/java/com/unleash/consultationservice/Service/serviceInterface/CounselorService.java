package com.unleash.consultationservice.Service.serviceInterface;

import com.unleash.consultationservice.DTO.CounselorDashBoardDTO;
import com.unleash.consultationservice.Model.CounselorAvilability;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CounselorService {
    boolean setSlot(List<String> list, String username);

    List findSlotmyslots(String date, String username);

    List findAvilableCounselors();

    List<CounselorAvilability> getAvilability(int counselorId, String date);

    boolean removeSlotOnDate(int counsId, String date);

    boolean removeSingleSlot(int userId, String date);

    ResponseEntity<?> getAccountDetails(int userId);

    CounselorDashBoardDTO getDashboardData(int userId);
}

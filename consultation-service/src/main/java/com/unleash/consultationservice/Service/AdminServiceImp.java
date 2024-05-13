package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.DTO.APIResponse;
import com.unleash.consultationservice.DTO.SessionDto;
import com.unleash.consultationservice.DTO.UserDto;
import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Model.Plans;
import com.unleash.consultationservice.Model.SessionBooking;
import com.unleash.consultationservice.Repository.PlanRepo;
import com.unleash.consultationservice.Repository.SessionBookingRepo;
import com.unleash.consultationservice.Service.serviceInterface.AdminServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImp implements AdminServic {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    PlanRepo planRepo;

    @Autowired
    UserClient userClient;

    @Autowired
    SessionBookingRepo sessionBookingRepo;

    @Override
    public boolean addPlan(Map data , MultipartFile file) throws IOException {
        try{
            Plans plans = new Plans();
            plans.setPlanName((String) data.get("planName"));
            plans.setDescription((String) data.get("description"));
            plans.setPrice(new BigDecimal(String.valueOf(data.get("price"))));
            plans.setNoOfSession(Integer.parseInt((String) data.get("noOfSession")));
            String imgpath= cloudinaryService.upload(file);
            plans.setIconUrl(imgpath);
            planRepo.save(plans);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Plans> findAllPlans(){
        return planRepo.findAll();

    }

    @Override
    public ResponseEntity<?> getAllBookingDetails(int pageNo) {
        try{
            Page<SessionBooking> bookings = sessionBookingRepo.findAll(PageRequest.of(pageNo,10).withSort(Sort.by("id").descending()));
            List<UserDto> counselors = userClient.findAllCounselors();
            List<SessionDto> dtos = new ArrayList<>();
            for (SessionBooking booking : bookings) {
                SessionDto dto = new SessionDto();
                dto.setSessionBooking(booking);
                String counselorName = counselors.stream()
                        .filter(couns -> couns.getId() == booking.getAvilability().getUserId())
                        .map(couns -> couns.getFullname())
                        .findFirst()
                        .orElse(null);
                dto.setCounselorName(counselorName);
                dto.setCounselorId(booking.getAvilability().getUserId());
                dtos.add(dto);

            }

            return ResponseEntity.ok().body(new APIResponse<>(bookings.getNumber(),bookings.getSize(),bookings.getTotalPages(),dtos));
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();

    }
}

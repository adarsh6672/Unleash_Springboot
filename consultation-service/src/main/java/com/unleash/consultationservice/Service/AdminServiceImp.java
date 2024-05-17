package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.DTO.*;
import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Model.Plans;
import com.unleash.consultationservice.Model.SessionBooking;
import com.unleash.consultationservice.Repository.PlanRepo;
import com.unleash.consultationservice.Repository.SessionBookingRepo;
import com.unleash.consultationservice.Repository.SubscriptionPaymentRepo;
import com.unleash.consultationservice.Repository.SubscriptionRepo;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
     SubscriptionPaymentRepo subscriptionPaymentRepo;

    @Autowired
    SubscriptionRepo subscriptionRepo;

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

    @Override
    public DashboardDTO getDashboardData() {
        try{
            DashboardDTO dashboardDTO = userClient.getAdminDashboradData().getBody();
            dashboardDTO.setTodayIncome(subscriptionPaymentRepo.getTotalAmountForToday());
            dashboardDTO.setActiveSubscribers(subscriptionRepo.countBySessionCountGreaterThan(0));
            return dashboardDTO;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SubscriptionChartDTO> getSubscriptionChartData(String wise) {

        try{
            if(wise.equals("DAILY")){
                List<SubscriptionChartDTO> list = new ArrayList<>();

                for(int i=6 ; i>=0 ; i--){
                    LocalDate date = LocalDate.now().minusDays(i);
                    SubscriptionChartDTO dto = new SubscriptionChartDTO();
                    dto.setX(date.toString());
                    dto.setY(subscriptionPaymentRepo.getTotalAmountForGivenDate(date));
                    list.add(dto);

                }
                return list;
            } else if (wise.equals("MONTHLY")) {
                List<SubscriptionChartDTO> list = new ArrayList<>();
                int  totalMonth = LocalDate.now().getMonthValue();
                LocalDate date = LocalDate.now();
                for(int i=0 ; i<totalMonth;i++){
                    SubscriptionChartDTO dto = new SubscriptionChartDTO();
                    dto.setX(Month.of(i+1).toString());
                    dto.setY((subscriptionPaymentRepo.getTotalAmountForMonth(date.getYear() , i)));
                    list.add(dto);
                }
                return list;
            }
            return null;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

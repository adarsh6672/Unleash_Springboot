package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.DTO.BookingResponseDto;
import com.unleash.consultationservice.DTO.SessionDto;
import com.unleash.consultationservice.DTO.UserDashboardResponseDto;
import com.unleash.consultationservice.DTO.UserDto;
import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Model.CounselorAvilability;
import com.unleash.consultationservice.Model.Plans;
import com.unleash.consultationservice.Model.SessionBooking;
import com.unleash.consultationservice.Model.Subscription;
import com.unleash.consultationservice.Model.Util.SessionBookingComparator;
import com.unleash.consultationservice.Model.Util.Status;
import com.unleash.consultationservice.Repository.CounselorAvailabilityRepo;
import com.unleash.consultationservice.Repository.SessionBookingRepo;
import com.unleash.consultationservice.Repository.SubscriptionRepo;
import com.unleash.consultationservice.Service.serviceInterface.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SessionServiceImp implements SessionService {

    @Autowired
    private CounselorAvailabilityRepo counselorAvailabilityRepo;

    @Autowired
    private SessionBookingRepo sessionBookingRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private UserClient userClient;


    @Override
    @Transactional
    public ResponseEntity bookSession(int userId, int slotId) {
        SessionBooking booking= new SessionBooking();
        try {
            CounselorAvilability avilability = counselorAvailabilityRepo.findById(slotId).orElseThrow();
            if (!avilability.isBooked()) {
                Subscription subscription = subscriptionRepo.findLatestSubscriptionByUserId(userId).orElseThrow();
                subscription.setSessionCount(subscription.getSessionCount() - 1);
                avilability.setBooked(true);
                booking.setBookingTime(LocalDateTime.now());
                booking.setAvilability(avilability);
                booking.setPatientId(userId);
                booking.setStatus(Status.BOOKED);
                sessionBookingRepo.save(booking);
                counselorAvailabilityRepo.save(avilability);
                subscriptionRepo.save(subscription);

                BookingResponseDto responseDto = new BookingResponseDto();
                responseDto.setCounselor(userClient.findUsername(avilability.getUserId()));
                responseDto.setSessionId(booking.getId());
                responseDto.setBookedOn(avilability.getSlot());
                return ResponseEntity.ok().body(responseDto);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> getDashBoardData(int userId) {
        try {
            Subscription subscription = subscriptionRepo.findLatestSubscriptionByUserId(userId).orElse(null);
            Plans plan = subscription.getPlans();
            SessionBooking booking = sessionBookingRepo.findLatestSessionBookingByUserId(userId).orElse(null);
            String counselorName=null;
            int i = 0;
            if(booking!=null){
                counselorName = userClient.findUsername(booking.getAvilability().getUserId());
                i=booking.getAvilability().getUserId();
            }

            UserDashboardResponseDto dto = new UserDashboardResponseDto();
            dto.setPlan(plan);
            dto.setSessionBooking(booking);
            dto.setCounselorName(counselorName);
            dto.setCounselorId(i);
            if(subscription!=null){
                dto.setSubscription(subscription);
            }
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<?> findAllBookingsByUser(int userId){
        try {

            List<SessionBooking> list = sessionBookingRepo.findByPatientId(userId);
            Collections.sort(list, new SessionBookingComparator());
            List<UserDto> counselors = userClient.findAllCounselors();
            System.out.println(counselors);
            List<SessionDto> dtos = new ArrayList<>();
            for (SessionBooking booking : list) {
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

            return ResponseEntity.ok().body(dtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> cancelSession(int slotId) {
        try {
            SessionBooking booking =sessionBookingRepo.findById(slotId).orElseThrow();
            booking.setStatus(Status.CANCELED);
            CounselorAvilability avilability = booking.getAvilability();
            int patientId = booking.getPatientId();
            Subscription subscription=subscriptionRepo.findLatestSubscriptionByUserId(patientId).orElseThrow();
            subscription.setSessionCount(subscription.getSessionCount()+1);
            avilability.setBooked(false);
            sessionBookingRepo.save(booking);
            counselorAvailabilityRepo.save(avilability);
            subscriptionRepo.save(subscription);
            return ResponseEntity.ok().body("booking Cancelled");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }


    }
}

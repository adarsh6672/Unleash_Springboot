package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.DTO.BookingResponseDto;
import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Model.CounselorAvilability;
import com.unleash.consultationservice.Model.SessionBooking;
import com.unleash.consultationservice.Model.Subscription;
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
                Subscription subscription = subscriptionRepo.findLatestSubscriptionByUserId(userId);
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
}

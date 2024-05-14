package com.unleash.consultationservice.Service.serviceInterface;

import com.unleash.consultationservice.DTO.FeedbackDto;
import org.springframework.http.ResponseEntity;

public interface SessionService {
    

    ResponseEntity bookSession(int userId, int slotId);

    ResponseEntity<?> getDashBoardData(int userId);

    ResponseEntity<?> findAllBookingsByUser(int userId);

    ResponseEntity<?> cancelSession(int slotId);

    ResponseEntity<?> findAllBookingsForCounselor(int userId);

    ResponseEntity<?> submitFeedback(FeedbackDto feedbackDto);

    ResponseEntity<?> getfeedbackofCounselor(int counselorId, int pageNo);
}

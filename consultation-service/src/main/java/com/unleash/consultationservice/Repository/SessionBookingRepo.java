package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.SessionBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionBookingRepo extends JpaRepository<SessionBooking , Integer> {
}

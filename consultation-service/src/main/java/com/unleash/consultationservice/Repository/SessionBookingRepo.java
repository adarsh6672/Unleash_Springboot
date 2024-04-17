package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.SessionBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SessionBookingRepo extends JpaRepository<SessionBooking , Integer> {

    @Query("SELECT s FROM SessionBooking s WHERE s.patientId = :userId AND s.status = 0 ORDER BY s.id DESC LIMIT 1")
    Optional<SessionBooking> findLatestSessionBookingByUserId(int userId);

    List<SessionBooking> findByPatientId(int patientId);

}

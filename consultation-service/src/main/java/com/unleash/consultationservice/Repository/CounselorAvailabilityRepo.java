package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.CounselorAvilability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface CounselorAvailabilityRepo extends JpaRepository<CounselorAvilability , Integer> {

    /*List<CounselorAvilability> findByUserIdAndSlotGreaterThanEqual(int userId, ZonedDateTime today);*/



    List<CounselorAvilability> findByUserIdAndSlotBetween(int userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    /*Optional<CounselorAvilability> findFirstByUserIdAndSlotAfterAndIsBookedFalseOrderBySlotAsc(int userId, ZonedDateTime currentDateTime);*/

    @Query(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY slot) as rn FROM counselor_avilability WHERE slot > :currentTime AND is_booked = false) tmp WHERE rn = 1", nativeQuery = true)
    List<CounselorAvilability> findNextAvailableSlots(@Param("currentTime") LocalDateTime currentTime);


}

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

    List<CounselorAvilability> findByUserId(int userId);

    List<CounselorAvilability> findByUserIdAndSlotBetween(int userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    void deleteByUserIdAndSlotBetween(int userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    void deleteByUserIdAndSlot(int userId, LocalDateTime dateTime);

    /*Optional<CounselorAvilability> findFirstByUserIdAndSlotAfterAndIsBookedFalseOrderBySlotAsc(int userId, ZonedDateTime currentDateTime);*/

    @Query(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY slot) as rn FROM counselor_avilability WHERE slot > :currentTime AND is_booked = false) tmp WHERE rn = 1", nativeQuery = true)
    List<CounselorAvilability> findNextAvailableSlots(@Param("currentTime") LocalDateTime currentTime);



        @Query(value = "SELECT COUNT(*) FROM counselor_avilability WHERE user_id = :userId AND is_booked = true AND slot >= :startDate AND slot <= :endDate", nativeQuery = true)
        int countBookedSessionsByUserIdAndDate(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    @Query(value = "SELECT COUNT(*) FROM counselor_avilability WHERE user_id = :userId AND is_booked = true", nativeQuery = true)
    int countBookedSessionsByUserId(@Param("userId") int userId);
}

package com.unleash.userservice.Reposetory;

import com.unleash.userservice.Model.CounselorAvilability;
import com.unleash.userservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CounselorAvilabilityRepository extends JpaRepository<CounselorAvilability , Integer> {
        List<CounselorAvilability> findByUserAndSlotGreaterThanEqual(User user, LocalDateTime today);



    List<CounselorAvilability> findByUserAndSlotBetween(User user, LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<CounselorAvilability> findFirstByUserAndSlotAfterAndIsBookedFalseOrderBySlotAsc(User user, LocalDateTime currentDateTime);
}

package com.unleash.userservice.Reposetory;

import com.unleash.userservice.Model.CounselorAvilability;
import com.unleash.userservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CounselorAvilabilityRepository extends JpaRepository<CounselorAvilability , Integer> {
    List<CounselorAvilability> findByUserAndSlotGreaterThanEqual(User user, LocalDateTime today);
}

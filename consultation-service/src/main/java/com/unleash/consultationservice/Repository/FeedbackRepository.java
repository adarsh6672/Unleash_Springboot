package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.Feedback;
import com.unleash.consultationservice.Model.WeekData;
import com.unleash.consultationservice.Model.WeeklySession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback , Integer> {
    Page<Feedback> findByCounselorId(int counselorId , Pageable pageable);
}

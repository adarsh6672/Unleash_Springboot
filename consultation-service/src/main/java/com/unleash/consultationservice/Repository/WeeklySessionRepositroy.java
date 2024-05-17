package com.unleash.consultationservice.Repository;


import com.unleash.consultationservice.Model.WeekData;
import com.unleash.consultationservice.Model.WeeklySession;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeeklySessionRepositroy extends JpaRepository<WeeklySession ,Integer> {

    Page<WeeklySession> findByWeek(WeekData week , Pageable pageable);

    @Query("SELECT SUM(w.amount) FROM WeeklySession w WHERE w.userId = :userId AND w.isPayed = true")
    Double getTotalAmountForPaidSessions(@Param("userId") int userId);

    @Query("SELECT SUM(w.amount) FROM WeeklySession w WHERE w.userId = :userId AND w.isPayed = false")
    Double getTotalAmountForUnPaidSessions(@Param("userId") int userId);
}

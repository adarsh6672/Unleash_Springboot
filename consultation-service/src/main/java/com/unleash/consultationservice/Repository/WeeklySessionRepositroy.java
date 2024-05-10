package com.unleash.consultationservice.Repository;


import com.unleash.consultationservice.Model.WeekData;
import com.unleash.consultationservice.Model.WeeklySession;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface WeeklySessionRepositroy extends JpaRepository<WeeklySession ,Integer> {

    Page<WeeklySession> findByWeek(WeekData week , Pageable pageable);
}

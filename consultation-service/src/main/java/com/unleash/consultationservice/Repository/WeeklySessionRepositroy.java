package com.unleash.consultationservice.Repository;


import com.unleash.consultationservice.Model.WeekData;
import com.unleash.consultationservice.Model.WeeklySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklySessionRepositroy extends JpaRepository<WeeklySession ,Integer> {

    List<WeeklySession> findByWeek(WeekData week);
}

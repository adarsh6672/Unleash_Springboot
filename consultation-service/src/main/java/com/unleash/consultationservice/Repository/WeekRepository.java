package com.unleash.consultationservice.Repository;


import com.unleash.consultationservice.Model.WeekData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<WeekData, Integer> {


    WeekData findFirstByOrderByIdDesc();
}

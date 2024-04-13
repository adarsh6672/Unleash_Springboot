package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.Plans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepo extends JpaRepository<Plans , Integer> {
    List<Plans> findByIsHiddenFalse();
}

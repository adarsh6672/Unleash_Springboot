package com.unleash.userservice.Reposetory;

import com.unleash.userservice.Model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender , Integer> {
}

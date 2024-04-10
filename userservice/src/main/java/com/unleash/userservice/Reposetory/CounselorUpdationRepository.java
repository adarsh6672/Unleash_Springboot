package com.unleash.userservice.Reposetory;

import com.unleash.userservice.Model.ConselorUpdations;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounselorUpdationRepository extends JpaRepository<ConselorUpdations , Integer> {

    Optional<ConselorUpdations> findByUser(User user);
}

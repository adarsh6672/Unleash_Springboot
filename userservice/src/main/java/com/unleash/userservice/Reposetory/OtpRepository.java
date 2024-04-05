package com.unleash.userservice.Reposetory;

import com.unleash.userservice.Model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp , Integer> {

    Optional<Otp> findByEmail(String email);

    void deleteByEmail(String email);

    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime dateTime);
}

package com.unleash.userservice.Reposetory;

import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CounselorDateRepository extends JpaRepository<CounselorData , Integer> {
    Optional<CounselorData> findByUser(User user);

    List<CounselorData> findByIsVerifiedTrue();

    List<CounselorData> findByIsVerifiedFalse();
}

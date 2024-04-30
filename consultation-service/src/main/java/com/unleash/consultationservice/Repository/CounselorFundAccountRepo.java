package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.CounselorFundAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounselorFundAccountRepo extends JpaRepository<CounselorFundAccount , Integer> {

    Optional<CounselorFundAccount> findByUserId(int userId);

}

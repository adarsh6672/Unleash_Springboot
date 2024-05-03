package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.CounselorTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CounselorTransactionRepository extends JpaRepository<CounselorTransactions , Integer> {
    Optional<List<CounselorTransactions>> findByUserIdOrderByIdDesc(int userId);
}

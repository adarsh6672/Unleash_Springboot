package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubscriptionRepo extends JpaRepository<Subscription , Integer> {

  /*  @Query("SELECT s FROM Subscription s WHERE s.userId = :userId AND s.startFrom = (SELECT MAX(s2.startFrom) FROM Subscription s2 WHERE s2.userId = :userId)")
    Subscription findLatestSubscriptionByUserId(@Param("userId") int userId);*/

    @Query("SELECT s FROM Subscription s WHERE s.userId = :userId ORDER BY s.id DESC LIMIT 1")
    Optional<Subscription> findLatestSubscriptionByUserId(int userId);


}

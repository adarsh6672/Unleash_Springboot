package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepo extends JpaRepository<Subscription , Integer> {
}

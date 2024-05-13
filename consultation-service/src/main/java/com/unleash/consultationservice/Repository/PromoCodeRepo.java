package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoCodeRepo extends JpaRepository<PromoCode , Integer> {

    PromoCode findByPromocode(String promocode);
}

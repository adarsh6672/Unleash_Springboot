package com.unleash.consultationservice.Service.serviceInterface;

import com.unleash.consultationservice.DTO.PromocodeDTO;
import org.springframework.http.ResponseEntity;

public interface PromocodeService {
    ResponseEntity<?> addPromocode(PromocodeDTO promocode);

    ResponseEntity<?> getAllPromocode(int pageNo);

    ResponseEntity<?> getPromocodeDetails(String promocode);
}

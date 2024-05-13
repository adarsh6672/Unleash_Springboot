package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.DTO.PromocodeDTO;
import com.unleash.consultationservice.Model.PromoCode;
import com.unleash.consultationservice.Repository.PromoCodeRepo;
import com.unleash.consultationservice.Service.serviceInterface.PromocodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PromocodeServiceImp implements PromocodeService {

    @Autowired
    private PromoCodeRepo promocodeRepository;

    @Override
    public ResponseEntity<?> addPromocode(PromocodeDTO data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
        PromoCode promocode = new PromoCode();
        promocode.setPromocode(data.getPromocode());
        promocode.setDiscountAmount(data.getDiscountAmount());
        promocode.setMinimumAmount(data.getMinimumAmount());
        promocode.setStartDate(LocalDateTime.parse(data.getStartDate(), formatter));
        promocode.setEndDate(LocalDateTime.parse(data.getEndDate(),formatter));
        promocode.setActive(true);
        promocodeRepository.save(promocode);
        return ResponseEntity.ok().body("successfully Created Promocode");
    }

    @Override
    public ResponseEntity<?> getAllPromocode(int pageNo) {
        Page<PromoCode> promoCodes = promocodeRepository.findAll(PageRequest.of(pageNo, 5).withSort(Sort.by("id").descending()));
        return ResponseEntity.ok().body(promoCodes);
    }

    @Override
    public ResponseEntity<?> getPromocodeDetails(String promocode) {
        try {
            PromoCode promoCode= promocodeRepository.findByPromocode(promocode);
            if(promoCode.getEndDate().isAfter(LocalDateTime.now())){
                return ResponseEntity.ok().body(promoCode);
            }
           return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}

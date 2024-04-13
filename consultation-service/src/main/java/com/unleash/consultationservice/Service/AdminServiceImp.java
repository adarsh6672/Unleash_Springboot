package com.unleash.consultationservice.Service;

import com.unleash.consultationservice.Model.Plans;
import com.unleash.consultationservice.Repository.PlanRepo;
import com.unleash.consultationservice.Service.serviceInterface.AdminServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImp implements AdminServic {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    PlanRepo planRepo;

    @Override
    public boolean addPlan(Map data , MultipartFile file) throws IOException {
        Plans plans = new Plans();
        plans.setPlanName((String) data.get("planName"));
        plans.setDescription((String) data.get("description"));
        plans.setPrice(new BigDecimal(String.valueOf(data.get("price"))));
        plans.setNoOfSession(Integer.parseInt((String) data.get("noOfSession")));
        String imgpath= cloudinaryService.upload(file);
        plans.setIconUrl(imgpath);
        planRepo.save(plans);
        return true;
    }

    @Override
    public List<Plans> findAllPlans(){
        return planRepo.findAll();

    }
}

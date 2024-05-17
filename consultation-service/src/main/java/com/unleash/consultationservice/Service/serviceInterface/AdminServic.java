package com.unleash.consultationservice.Service.serviceInterface;

import com.unleash.consultationservice.DTO.DashboardDTO;
import com.unleash.consultationservice.DTO.PlanDto;
import com.unleash.consultationservice.DTO.SubscriptionChartDTO;
import com.unleash.consultationservice.Model.Plans;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AdminServic {

     boolean addPlan(Map planDto, MultipartFile file) throws IOException;

    List<Plans> findAllPlans();

    ResponseEntity<?> getAllBookingDetails(int pageNo);

    DashboardDTO getDashboardData();

    List<SubscriptionChartDTO> getSubscriptionChartData(String wise);
}

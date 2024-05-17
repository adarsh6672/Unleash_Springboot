package com.unleash.consultationservice.Repository;

import com.unleash.consultationservice.Model.SubscriptionPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionPaymentRepo extends JpaRepository<SubscriptionPayments , Integer> {

    SubscriptionPayments findByOrderId(String orderId);

    @Query("SELECT SUM(amount) FROM SubscriptionPayments WHERE DATE(payedOn) = CURRENT_DATE")
    BigDecimal getTotalAmountForToday();

    @Query("SELECT SUM(amount) FROM SubscriptionPayments WHERE DATE(payedOn) = :givenDate")
    BigDecimal getTotalAmountForGivenDate(@Param("givenDate") LocalDate givenDate);

    @Query("SELECT SUM(amount) FROM SubscriptionPayments WHERE YEAR(payedOn) = YEAR(CURRENT_DATE) AND MONTH(payedOn) = MONTH(CURRENT_DATE)")
    BigDecimal getTotalAmountForCurrentMonth();

    @Query("SELECT SUM(amount) FROM SubscriptionPayments WHERE YEAR(payedOn) = :year AND MONTH(payedOn) = :month")
    BigDecimal getTotalAmountForMonth(@Param("year") int year, @Param("month") int month);




}

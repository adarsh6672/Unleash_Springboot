package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.OtpDto;
import com.unleash.userservice.Model.Otp;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.OtpRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImp implements OtpService {

    private final EmailServiceImp emailServiceImp;

    private final UserRepository repository;

    private final OtpRepository otpRepository;

    @Autowired
    public OtpServiceImp(EmailServiceImp emailServiceImp, UserRepository repository, OtpRepository otpRepository) {
        this.emailServiceImp = emailServiceImp;
        this.repository = repository;
        this.otpRepository = otpRepository;
    }

    @Override
    public String generateOTP(User user) {
        Random random = new Random();
        String otp=  String.format("%06d", random.nextInt(999999));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("OTP Verification");
        mailMessage.setText("Dear "+user.getFullname()+" , Your OTP is "+otp+" for Unleash. Thanks , Unleash Team");
        Otp otp1 = getOrBuid(user.getEmail());
        otp1.setEmail(user.getEmail());
        otp1.setCreatedAt(LocalDateTime.now());
        otp1.setOtp(otp);
        otpRepository.save(otp1);
        try {
            emailServiceImp.sendEmail(mailMessage);
        }catch (Exception e){
            System.out.println("email sending failed");
            otpRepository.deleteByEmail(user.getEmail());
        }
        return otp;
    }

    @Override
    public boolean validateOTP(OtpDto dto) {
        Otp saved=otpRepository.findByEmail(dto.getEmail()).orElseThrow();
        if(saved.getOtp().equals(dto.getOtp())){
            otpRepository.deleteById(saved.getId());
            return true;
        }
        return false;
    }

    @Override
    public String  forgotPassword(String email){
        User user=repository.findByEmail(email).orElseThrow();
            return  generateOTP(user);

    }

    public Otp getOrBuid(String email){
        Optional<Otp> otp =otpRepository.findByEmail(email);
        if(otp.isPresent()){
            return otp.get();
        }
        return new Otp();
    }

    @Scheduled(cron = "0 * * * * ?") // This will run every minute
    @Transactional
    public void deleteOldOTPs() {
        LocalDateTime twoMinutesAgo = LocalDateTime.now().minus(2, ChronoUnit.MINUTES);
        otpRepository.deleteByCreatedAtBefore(twoMinutesAgo);
    }

}

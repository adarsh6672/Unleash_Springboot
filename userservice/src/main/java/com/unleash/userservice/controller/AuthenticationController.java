package com.unleash.userservice.controller;


import com.unleash.userservice.DTO.OtpDto;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Service.AuthenticationServiceImp;
import com.unleash.userservice.Service.services.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationServiceImp authService;
    private final OtpService otpService;
    private User req;





    @Autowired
    public AuthenticationController(AuthenticationServiceImp authService, OtpService otpService, HttpServletRequest httpServletRequest) {
        this.authService = authService;
        this.otpService = otpService;
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request){
        req= request;
        if(authService.isEmailExisting(request.getEmail())){
            return ResponseEntity.badRequest().body("email id already existing");
        }
        String sent = otpService.generateOTP(request );
        return ResponseEntity.ok().body("otp send to mail id  "+sent);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request){
        if(authService.isBlocked(request)){
           return ResponseEntity.badRequest().body("Account is Blocked");
        }
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("otp/verify")
    public ResponseEntity<?> verifyotp(@RequestBody OtpDto otpDto ){
        if(otpDto!=null){
            if(otpService.validateOTP(otpDto)){
                return ResponseEntity.ok(authService.register(req));
            }
        }
        return ResponseEntity.badRequest().body("invalid otp");
    }

    @PostMapping("password/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody OtpDto dto ){
        System.out.println(dto.getEmail());
        if(authService.isEmailExisting(dto.getEmail())){
            String send = otpService.forgotPassword(dto.getEmail());
            return ResponseEntity.ok().body("yes it is there "+ dto.getEmail());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("password/forgot/otpverify")
    public ResponseEntity<?> otpverify(@RequestBody OtpDto dto){
        if(otpService.validateOTP(dto)){
            return ResponseEntity.ok().body("otp verified");
        }
        return ResponseEntity.badRequest().body("invalid otp");
    }

    @PostMapping("password/forgot/newpassword")
    public ResponseEntity<?> updatePassword(@RequestBody OtpDto dto){

        if(authService.updatePassword(dto)){
            return ResponseEntity.ok().body("password updated successfully");
        }
        return ResponseEntity.badRequest().build();
    }


}

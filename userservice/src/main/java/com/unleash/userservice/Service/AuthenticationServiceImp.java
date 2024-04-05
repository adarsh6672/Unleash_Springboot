package com.unleash.userservice.Service;




import com.unleash.userservice.DTO.OtpDto;
import com.unleash.userservice.DTO.AuthenticationResponse;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.Role;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.CounselorDateRepository;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private  final UserRepository repository;

    private final CounselorDateRepository counselorDateRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImp jwtService;

    private final EmailServiceImp emailServiceImp;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImp(UserRepository repository, CounselorDateRepository counselorDateRepository, PasswordEncoder passwordEncoder, JwtServiceImp jwtService, EmailServiceImp emailServiceImp, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.counselorDateRepository = counselorDateRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailServiceImp = emailServiceImp;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(User request){
        User user= new User();
        try{
            user.setPhone(request.getPhone());
            user.setFullname((request.getFullname()));
            user.setEmail(request.getEmail());
            user.setUsername(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            user.setJoinedOn(LocalDateTime.now());
            user.setBlocked(false);
            user= repository.save(user);
            if(user.getRole().equals(Role.COUNSELOR)){
                CounselorData data= new CounselorData(user);
                counselorDateRepository.save(data);
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }


        String token = jwtService.generateToken(user);

        AuthenticationResponse response= new AuthenticationResponse(token , String.valueOf(request.getRole()));
        if(user.getRole().equals(Role.COUNSELOR)){
           CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();
           if(!counselorData.isVerified()){
               response.setRole("Unverified");
           }

        }
        return  response;
    }

    @Override
    public AuthenticationResponse authenticate(User request){
        System.out.println(request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        System.out.println(user.getFullname()+"--------------------------------------");
        String role = String.valueOf(user.getRole());

        String token = jwtService.generateToken(user);

        AuthenticationResponse response= new AuthenticationResponse(token , String.valueOf(user.getRole()));
        if(user.getRole().equals(Role.COUNSELOR)){
            CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();
            if(!counselorData.isVerified()){
                response.setRole("Unverified");
            }

        }
        return response;
    }

    @Override
    public boolean isBlocked(User request){
        User user = repository.findByEmail(request.getUsername()).orElseThrow();
        if(user.isBlocked()){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public boolean isEmailExisting(String email){
        return repository.existsByEmail(email);
    }

    @Override
    public boolean updatePassword(OtpDto dto){
        try{
            User user = repository.findByEmail(dto.getEmail()).orElseThrow();
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            repository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
package com.unleash.userservice.Service.services;

import com.unleash.userservice.Model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);

    boolean isValid(String token, UserDetails user);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> resolver);

    Claims extractAllClaims(String token);

    String generateToken(User user);

    SecretKey getSigninKey();
}

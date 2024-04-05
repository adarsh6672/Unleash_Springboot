package com.unleash.userservice.Service;


import com.unleash.userservice.Model.User;
import com.unleash.userservice.Service.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImp implements JwtService {

    private final String SECRET_KEY ="9210a0e16daf5f651d1a6d233e6426a569237c5eb2d0bc63c6cb19f7cebb1a10";



    /*public JwtServiceImp(@Value("${jwt.secret.key}") String secretKey) {
        SECRET_KEY = secretKey;
    }*/

    @Override
    public  String extractUsername(String token){
        return extractClaim(token , Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, UserDetails user){
        String username= extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims= extractAllClaims((token));
        return  resolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String generateToken(User user){
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 24*60*60*1000))
                .signWith(getSigninKey())
                .compact();
        System.out.println(token);
        return  token;
    }

    @Override
    public SecretKey getSigninKey(){
        byte[] KeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
}
package com.unleash.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET_KEY ="9210a0e16daf5f651d1a6d233e6426a569237c5eb2d0bc63c6cb19f7cebb1a10";

    public void validateToken(final String token){
        Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(token);
    }

    public String extractRole(String token) {
        String role= extractAllClaims(token).get("roles").toString();
        return role;
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).get("userId").toString();
    }




    public  String extractUsername(String token){
        return extractClaim(token , Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims= extractAllClaims((token));
        return  resolver.apply(claims);
    }


    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey getSigninKey(){
        byte[] KeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
}

package com.example.student_service.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretKey;// Secret key used for signing JWT
    public JwtService(@Value("${secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }
    // Generate JWT Token
    public String generateToken(String email,String role) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("role",role);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // Expiration time (1 hour)
                .and()
                .signWith(getkey())
                .compact();
    }

    private SecretKey getkey() {
        byte[] key=secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(key);
    }

    public String extractemail(String token) {
        try {
            return extractClaims(token, Claims::getSubject);
        } catch (JwtException e) {
            throw new SecurityException("Invalid JWT token", e);
        }
    }

    public String extractRole(String token) {
        return extractClaims(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        Claims claims=extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validToken(String token, UserDetails userDetails) {
        final String username= extractemail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }


}

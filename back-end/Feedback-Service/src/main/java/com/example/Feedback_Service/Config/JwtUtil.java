package com.example.Feedback_Service.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String secretKey = "u7pD1Xb2i7Jkz+vQn8s5VtXpKYiBnsY5wtFWmeIoH2I=";
    private SecretKey getkey() {
        byte[] key= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
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

    public boolean isAdmin(String token) {
        try {
            Claims claims = extractClaims(token);
            String role = claims.get("role", String.class);
            return "ADMIN".equals(role);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}

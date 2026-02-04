package com.example.volgaProject.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMillis;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-ms}") long expirationMillis
    ){
        this.secretKey= Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis=expirationMillis;
    }

    public String generateToken(UUID userId, String role){
        Date now=new Date();
        Date expiryDate=new Date(now.getTime()+expirationMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .addClaims(Map.of("role",role))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;

        } catch (ExpiredJwtException ex) {
            // token expired – normal scenario
            log.debug("JWT expired");

        } catch (UnsupportedJwtException ex) {
            log.warn("JWT unsupported");

        } catch (MalformedJwtException ex) {
            log.warn("JWT malformed");

        } catch (SignatureException ex) {
            log.warn("JWT signature invalid");

        } catch (IllegalArgumentException ex) {
            log.warn("JWT claims empty");

        }
        return false;
    }


    public UUID extractUserId(String token){
        Claims claims=parseClaims(token);
        return UUID.fromString(claims.getSubject());
    }


    public String extractRole(String token) {
        Claims claims = parseClaims(token);
        return claims.get("role", String.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



}

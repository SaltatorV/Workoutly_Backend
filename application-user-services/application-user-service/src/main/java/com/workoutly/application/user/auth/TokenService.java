package com.workoutly.application.user.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    private final int MILISECONDS_RATIO = 1000;
    private final JwtTokenConfiguration properties;

    public TokenService(JwtTokenConfiguration properties) {
        this.properties = properties;
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String generateNewToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issueNow())
                .setExpiration(calculateExpiration())
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    boolean isValidForUser(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return !isTokenExpired(token) && username.equals(userDetails.getUsername());
    }

    private Boolean isTokenExpired(String token) {
        return getAllClaimsFromToken(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    private Date issueNow(){
        return new Date(System.currentTimeMillis());
    }

    private Date calculateExpiration() {
        return new Date(System.currentTimeMillis() + properties.getValidity() * MILISECONDS_RATIO);
    }
}
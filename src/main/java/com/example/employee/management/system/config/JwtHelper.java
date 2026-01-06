package com.example.employee.management.system.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final Date tokenExpirationDate = extractClaim(token, Claims::getExpiration);

        boolean usernameMatch = Objects.equals(username, userDetails.getUsername());
        boolean tokenIsExpired = tokenExpirationDate.before(new Date(System.currentTimeMillis()));

        return usernameMatch && !tokenIsExpired;
    }

  public String generateToken(UserDetails userDetails) {
    String role = "ROLE_EMPLOYEE"; // default

    if (userDetails.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
        role = userDetails.getAuthorities().iterator().next().getAuthority();
    }

    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);

    return generateToken(claims, userDetails);
}


    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14))
                .signWith(getSignInKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

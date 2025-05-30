package com.demo.todo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String SECRET;

    // Public Methods
    public String generateToken(String username) {
        return createToken(new HashMap<>(), username);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUser(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



    public boolean validateToken(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails);
    }

    // Private Methods
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenValid(String token, UserDetails userDetails) {
        final String usernameInToken = extractUser(token);
        return (usernameInToken.equals(userDetails.getUsername())) && isTokenExpiredCheck(token);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();
        Jws<Claims> jwsClaims = parser.parseSignedClaims(token);
        return jwsClaims.getPayload();
    }


    private boolean isTokenExpiredCheck(String token) {
        return extractExpiration(token).after(new Date());
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }
}
package org.example.ticket_booking.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    //reads secret key from application.properties
    @Value("${jwt.secret}")
    private String secret;

    //reads expiry time from app.prop (24 hours in milliseconds)
    @Value("${jwt.expiration}")
    private Long expiration;

    //creates signing key from our secret string
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //generates a JWT token for a given username
    //called after successful login
    public String generateToken (String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + expiration
                ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //reads username out of a token
    public String extractUserName(String token){
        return getClaims(token).getSubject();
    }

    //checks if token is valid and not expired
    public boolean isTokenValid (String token){
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    //internal helper - parses and generates token content
    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

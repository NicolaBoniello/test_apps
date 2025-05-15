package com.example.applicazione_pub.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.expiration}")
    private Long expiration;

    public JwtUtil() {}

    public JwtUtil(String secret, Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(String email){
       return Jwts.builder()
        .setSubject(email) // Il soggetto del token è l'email dell'utente
                .setIssuedAt(new Date()) // Quando è stato generato
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Scadenza del token
                .signWith(SignatureAlgorithm.HS256, secret) // Firma il token con la chiave segreta
                .compact();

    }

    public String getSubject(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

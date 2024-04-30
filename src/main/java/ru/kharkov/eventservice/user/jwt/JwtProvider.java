package ru.kharkov.eventservice.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.user.User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {


    private final SecretKey signing;

    public JwtProvider(@Value("${jwt.secret.signing}") String jwtAccessSecret) {
        this.signing = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }


    public String generateToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(accessExpiration)
                .claim("role", user.getRole())
                .claim("login", user.getLogin())
                .signWith(signing)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signing)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signing)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

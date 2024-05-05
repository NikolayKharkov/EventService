package ru.kharkov.eventservice.user.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kharkov.eventservice.user.User;
import ru.kharkov.eventservice.user.dto.JWTResponse;
import ru.kharkov.eventservice.user.dto.LogInRequest;
import ru.kharkov.eventservice.user.jwt.JwtProvider;

@Service
public class JwtService {

    @Autowired
    private JwtProvider jwtProvider;

    public JWTResponse generateToken(LogInRequest logInRequest, User user) {
        String token = this.jwtProvider.generateToken(user);
        return new JWTResponse(token);
    }

    public boolean validateToken (String token) {
        return this.jwtProvider.validateToken(token);
    }

    public Claims getClaims(String token) {
        return this.jwtProvider.getClaims(token);
    }
}

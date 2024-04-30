package ru.kharkov.eventservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kharkov.eventservice.user.dto.JWTResponse;
import ru.kharkov.eventservice.user.dto.LogInRequest;
import ru.kharkov.eventservice.user.jwt.JwtService;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User sighUp(User user) {
        String encryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);
        return this.userService.createUser(user);
    }

    public JWTResponse logIn(LogInRequest logInRequest) {
        User user = this.userService.getUserByLogin(logInRequest.getLogin());
        return this.jwtService.generateToken(logInRequest, user);
    }
}

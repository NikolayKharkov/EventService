package ru.kharkov.eventservice.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kharkov.eventservice.user.Role;
import ru.kharkov.eventservice.user.jwt.JwtAuthentication;

import java.util.Set;

@Service
public class SecurityService {

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserAuthId() {
        JwtAuthentication jwtAuthentication = this.getAuthInfo();
        return Long.valueOf((String) jwtAuthentication.getCredentials());
    }

    public Set<Role> getUsersAuthRoles() {
        JwtAuthentication jwtAuthentication = this.getAuthInfo();
        return jwtAuthentication.getRoles();
    }
}

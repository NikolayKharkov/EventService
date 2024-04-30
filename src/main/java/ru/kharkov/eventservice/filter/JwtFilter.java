package ru.kharkov.eventservice.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.kharkov.eventservice.user.*;
import ru.kharkov.eventservice.user.jwt.JwtAuthentication;
import ru.kharkov.eventservice.user.jwt.JwtService;

import java.io.IOException;
import java.util.Set;

@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null && this.jwtService.validateToken(token)) {
            final Claims claims = this.jwtService.getClaims(token);
            String userLogin = claims.get("login", String.class);
            if (this.userService.userExistByLogin(userLogin)) {
                final JwtAuthentication jwtInfoToken = new JwtAuthentication();
                jwtInfoToken.setRoles(Set.of(Role.valueOf(String.valueOf(claims.get("role")))));
                jwtInfoToken.setUserLogin(userLogin);
                jwtInfoToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
            }
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER_PREFIX)) {
            return bearer.substring(BEARER_PREFIX.length());
        }
        return null;
    }

}

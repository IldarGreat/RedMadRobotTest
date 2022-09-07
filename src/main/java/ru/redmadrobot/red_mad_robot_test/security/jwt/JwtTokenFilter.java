package ru.redmadrobot.red_mad_robot_test.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    @Value("${application.auth.cookie-name}")
    private String cookieName;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Cookie[] requestCookies = request.getCookies();
        if (requestCookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        for (Cookie cookie : requestCookies) {
            if (cookie.getName().equals(cookieName)) {
                token = cookie.getValue();
            }
        }
        if (token == null || token.isEmpty() || !jwtTokenUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                jwtTokenUtil.getLogin(token), null, Set.of(new SimpleGrantedAuthority(jwtTokenUtil.getRole(token)))));
        filterChain.doFilter(request, response);
    }
}

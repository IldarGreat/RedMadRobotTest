package ru.redmadrobot.red_mad_robot_test.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.redmadrobot.red_mad_robot_test.dto.LoginRecord;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private final String base64Secret;
    private final Duration expirationTime;

    public JwtTokenUtil(@Value("${application.auth.jwt-secret}") String secretKey,
                        @Value("${application.auth.jwt-expiration-time}") Duration expirationTime) {
        this.base64Secret =
                Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;
    }

    public String createToken(LoginRecord loginRecord) {
        Claims claims = Jwts.claims().setSubject(loginRecord.email());
        claims.put("role", loginRecord.role());
        Instant issuedAt = Instant.now();
        Instant expireAt = issuedAt.plus(this.expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expireAt))
                .signWith(SignatureAlgorithm.HS256, base64Secret)
                .compact();
    }

    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(base64Secret)
                .parseClaimsJws(token);
        return !claimsJws
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String getLogin(String token) {
        return Jwts.parser()
                .setSigningKey(base64Secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRole(String token) {
        return (String) Jwts.parser()
                .setSigningKey(base64Secret)
                .parseClaimsJws(token)
                .getBody().get("role");
    }

}


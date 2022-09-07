package ru.redmadrobot.red_mad_robot_test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.redmadrobot.red_mad_robot_test.dto.LoginRecord;
import ru.redmadrobot.red_mad_robot_test.security.jwt.JwtTokenUtil;
import ru.redmadrobot.red_mad_robot_test.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Value("${application.auth.cookie-name}")
    private String cookieName;

    public LoginController(UserService userService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/registration", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registration(@RequestBody LoginRecord loginRecord, HttpServletResponse response) {
        userService.save(loginRecord);
        response.addCookie(new Cookie(cookieName, jwtTokenUtil.createToken(loginRecord)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRecord loginRecord, HttpServletResponse response) {
        if (loginRecord.email() == null || loginRecord.password() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRecord.email(), loginRecord.password()));
        response.addCookie(new Cookie(cookieName, jwtTokenUtil.createToken(loginRecord)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

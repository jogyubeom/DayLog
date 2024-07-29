package com.daylog.domain.security.controller;

import com.daylog.domain.security.annotation.UserId;
import com.daylog.domain.security.dto.JwtToken;
import com.daylog.domain.security.dto.SignInDto;
import com.daylog.domain.security.entity.User;
import com.daylog.domain.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();
        JwtToken jwtToken = userService.signIn(email, password);
        log.info("request email = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/users")
    public User signUp(@RequestBody User user) {
        return userService.signUp(user);
    }

    @GetMapping("/test")
    public String test(@UserId int userId) {
        return "UserID: " + userId;
    }
}
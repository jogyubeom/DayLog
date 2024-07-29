package com.daylog.domain.jwt.controller;

import com.daylog.domain.jwt.service.JwtService;
import com.daylog.domain.user.dto.LoginRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jwt")
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<String> getMemberProfile(@Valid @RequestBody LoginRequestDto request
    ) {
        String token = this.jwtService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}

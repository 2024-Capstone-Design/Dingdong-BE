package com.seoultech.capstone.domain.auth.controller;

import com.seoultech.capstone.domain.auth.dto.LoginRequest;
import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return "logout success";
    }

    @GetMapping(value = "/tokenRefresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenResponse refresh() {
        return authService.refreshToken();
    }

}

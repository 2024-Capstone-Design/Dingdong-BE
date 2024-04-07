package com.seoultech.capstone.domain.auth.jwt;

import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        throw new CustomException(ErrorCode.INVALID_TOKEN, "JwtAuthenticationEntryPoint: no valid credentials");

    }
}
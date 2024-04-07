package com.seoultech.capstone.domain.auth.service;

import com.seoultech.capstone.domain.auth.dto.LoginRequest;
import com.seoultech.capstone.domain.auth.jwt.TokenProvider;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

import static com.seoultech.capstone.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.refresh_expired-time}")
    long refreshExpired;

    public TokenResponse login(LoginRequest loginRequest) throws CustomException {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            TokenResponse tokenResponse = tokenProvider.createToken(authentication);

            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    tokenResponse.getRefreshToken(),
                    refreshExpired,
                    TimeUnit.SECONDS
            );

            return tokenResponse;
        } catch (BadCredentialsException e) {
            throw new CustomException(INVALID_AUTHORITY, "Invalid id or password");
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            redisTemplate.opsForValue().getAndDelete(authentication.getName());
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    public TokenResponse refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenResponse jwt = tokenProvider.createToken(authentication);
        redisTemplate.opsForValue().set(
                authentication.getName(),
                jwt.getRefreshToken(),
                refreshExpired,
                TimeUnit.SECONDS
        );
        return jwt;
    }


}

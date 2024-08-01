package com.seoultech.capstone.domain.user.student;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.user.student.dto.*;
import com.seoultech.capstone.domain.auth.jwt.TokenProvider;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.seoultech.capstone.response.ErrorStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.refresh_expired-time}")
    long refreshExpired;
    private final StudentRepository studentRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public LoginResponse login(StudentLoginRequest studentLoginRequest) throws CustomException {
        Student student = studentRepository.findByUsernameAndGroupIdAndActiveTrue(
                        studentLoginRequest.getUsername(), studentLoginRequest.getGroupId())
                .orElseThrow(() -> new CustomException(UNAUTHORIZED_INFO, "No such student with username " +studentLoginRequest.getUsername() + " and group ID " + studentLoginRequest.getGroupId()));

        try {
            TokenResponse tokenResponse = getAuthentication(student.getId(), studentLoginRequest.getPassword(), authenticationManagerBuilder, tokenProvider, redisTemplate, refreshExpired);

            return new LoginResponse(
                    "STUDENT",
                    tokenResponse.getAccessToken(),
                    tokenResponse.getRefreshToken()
            );
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorStatus.UNAUTHORIZED_INFO, "Invalid username or password");
        }
    }

    public static TokenResponse getAuthentication(Integer id, String password, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, RedisTemplate<String, String> redisTemplate, long refreshExpired) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                id, password
        );
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
    }

    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        Student student = studentRepository.findById(passwordResetRequest.getUserId())
                .orElseThrow(() -> new CustomException(UNAUTHORIZED_INFO, "No such student with id " +passwordResetRequest.getUserId() ));

        if (passwordEncoder.matches(passwordResetRequest.getOldPassword(), student.getPassword())) {
            student.updatePassword(passwordResetRequest.getNewPassword(), passwordEncoder);
            studentRepository.save(student);
        }
        else {
            throw new CustomException(UNAUTHORIZED_INFO, "Old password not match");
        }
    }
}

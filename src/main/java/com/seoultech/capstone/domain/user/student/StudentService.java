package com.seoultech.capstone.domain.user.student;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.user.student.dto.*;
import com.seoultech.capstone.domain.auth.jwt.TokenProvider;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.group.GroupRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.exception.ErrorCode;
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

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.seoultech.capstone.exception.ErrorCode.*;

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
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND, "No student found with the provided username " +studentLoginRequest.getUsername() + " and group ID " + studentLoginRequest.getGroupId()));

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    student.getId(), studentLoginRequest.getPassword()
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

            return new LoginResponse(
                    "STUDENT",
                    tokenResponse.getAccessToken(),
                    tokenResponse.getRefreshToken()
            );
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorCode.INVALID_AUTHORITY, "Invalid username or password");
        }
    }

    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        Student student = studentRepository.findById(passwordResetRequest.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND, "No student found with the provided id " +passwordResetRequest.getUserId() ));

        if (passwordEncoder.matches(passwordResetRequest.getOldPassword(), student.getPassword())) {
            student.updatePassword(passwordResetRequest.getNewPassword(), passwordEncoder);
            studentRepository.save(student);
        }
        else {
            throw new CustomException(INVALID_REQUEST, "Old password not match");
        }
    }
}

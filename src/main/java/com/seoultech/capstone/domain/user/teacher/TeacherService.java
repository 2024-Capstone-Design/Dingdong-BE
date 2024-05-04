package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.auth.jwt.TokenProvider;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.domain.organization.OrganizationRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.seoultech.capstone.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherService {

    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.refresh_expired-time}")
    long refreshExpired;
    private final OrganizationRepository organizationRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public TeacherSignupResponse teacherSignup(TeacherSignupRequest teacherSignupRequest) throws CustomException {
        try {

            String username = teacherSignupRequest.getEmail();

            if (teacherRepository.findByEmailAndActiveTrue(username).isPresent()) {
                throw new CustomException(DUPLICATED_MEMBER, "User already exists as teacher with username : " + teacherSignupRequest.getEmail());
            }

            String phoneNumber = teacherSignupRequest.getPhoneNumber();
            String lastFourDigits = phoneNumber.substring(phoneNumber.length() - 4);

            Teacher teacher = Teacher.builder()
                    .email(teacherSignupRequest.getEmail())
                    .password(passwordEncoder.encode(lastFourDigits))
                    .phoneNumber(teacherSignupRequest.getPhoneNumber())
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .name(teacherSignupRequest.getName())
                    .organization(organizationRepository.findById(teacherSignupRequest.getOrganizationId())
                            .orElseThrow(() -> new CustomException(ORGAN_NOT_FOUND, "No such organization")))
                    .serviceUsage(teacherSignupRequest.getServiceUsage())
                    .build();

            Teacher newTeacher = teacherRepository.save(teacher);

            return new TeacherSignupResponse(newTeacher.getId(), newTeacher.getEmail(), newTeacher.getName(), newTeacher.getOrganization());



        } catch (DataIntegrityViolationException e) {
            throw new CustomException(SERVER_ERROR, e.getMessage());
        }
    }


    public LoginResponse login(TeacherLoginRequest teacherLoginRequest) throws CustomException {
        Teacher teacher = teacherRepository.findByEmailAndActiveTrue(
                        teacherLoginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND, "No teacher found with the provided email " + teacherLoginRequest.getEmail()));

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    teacher.getId(), teacherLoginRequest.getPassword()
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
                    "TEACHER",
                    tokenResponse.getAccessToken(),
                    tokenResponse.getRefreshToken()
            );
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorCode.INVALID_AUTHORITY, "Invalid email or password");
        }
    }

}

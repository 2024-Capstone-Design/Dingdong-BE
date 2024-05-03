package com.seoultech.capstone.domain.user.student;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.user.student.dto.StudentLoginRequest;
import com.seoultech.capstone.domain.auth.jwt.TokenProvider;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.group.GroupRepository;
import com.seoultech.capstone.domain.user.student.dto.StudentSignupResponse;
import com.seoultech.capstone.domain.user.student.dto.StudentsSignupRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentsSignupResponse;
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
    private final GroupRepository groupRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public StudentsSignupResponse signupStudents(StudentsSignupRequest request) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new CustomException(GROUP_NOT_FOUND, "Invalid group ID: " + request.getGroupId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        List<StudentSignupResponse> signupResponses = request.getStudentSignupRequests().stream().map(studentRequest -> {
            String formattedBirth = studentRequest.getBirth().format(formatter);
            String baseUsername = studentRequest.getName() + "_" + formattedBirth;
            String username = baseUsername;

            // 중복 검사 및 조정
            int suffix = 0;
            while (studentRepository.existsByUsername(username)) {
                suffix++;
                username = baseUsername + (char)('a' + suffix - 1);
            }

            Student student = Student.builder()
                    .name(studentRequest.getName())
                    .birth(studentRequest.getBirth())
                    .username(username)
                    .password(passwordEncoder.encode(formattedBirth))
                    .group(group)
                    .active(true)
                    .build();

            student = studentRepository.save(student);

            return new StudentSignupResponse(student.getId(), student.getUsername(), student.getBirth());
        }).collect(Collectors.toList());

        return new StudentsSignupResponse(request.getGroupId(), signupResponses);
    }

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
}

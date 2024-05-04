package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.auth.jwt.TokenProvider;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.group.GroupRepository;
import com.seoultech.capstone.domain.organization.OrganizationRepository;
import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.domain.user.student.dto.PasswordResetRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentRegisterResponse;
import com.seoultech.capstone.domain.user.student.dto.StudentsRegisterResponse;
import com.seoultech.capstone.domain.user.student.dto.StudentsRegisterRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

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

    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        Teacher teacher = teacherRepository.findById(passwordResetRequest.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND, "No teacher found with the provided id " + passwordResetRequest.getUserId()));

        if (passwordEncoder.matches(passwordResetRequest.getOldPassword(), teacher.getPassword())) {
            teacher.updatePassword(passwordResetRequest.getNewPassword(), passwordEncoder);
            teacherRepository.save(teacher);
        }
        else {
            throw new CustomException(INVALID_REQUEST, "Old password not match");
        }
    }


    @Transactional
    public StudentsRegisterResponse registerStudents(StudentsRegisterRequest request) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new CustomException(GROUP_NOT_FOUND, "Invalid group ID: " + request.getGroupId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        List<StudentRegisterResponse> signupResponses = request.getStudentRegisterRequests().stream().map(studentRequest -> {
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

            return new StudentRegisterResponse(student.getId(), student.getUsername(), student.getBirth());
        }).collect(Collectors.toList());

        return new StudentsRegisterResponse(request.getGroupId(), signupResponses);
    }
}

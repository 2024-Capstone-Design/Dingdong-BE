package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.common.utils.SecurityUtils;
import com.seoultech.capstone.domain.auth.Enum.UserRole;
import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.auth.jwt.TokenProvider;
import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.group.GroupRepository;
import com.seoultech.capstone.domain.organization.OrganizationRepository;
import com.seoultech.capstone.domain.organization.OrganizationResponse;
import com.seoultech.capstone.domain.user.common.dto.ProfilePictureUpdateRequest;
import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.domain.user.student.StudentService;
import com.seoultech.capstone.domain.user.student.dto.*;
import com.seoultech.capstone.domain.user.teacher.dto.TeacherSignupRequest;
import com.seoultech.capstone.domain.user.teacher.dto.TeacherResponse;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.seoultech.capstone.response.ErrorStatus.*;

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

    public TeacherResponse teacherSignup(TeacherSignupRequest teacherSignupRequest) throws CustomException {
        try {

            String email = teacherSignupRequest.getEmail();

            if (teacherRepository.findByEmailAndActiveTrue(email).isPresent()) {
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
                            .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND, "No such organization with id "+ teacherSignupRequest.getOrganizationId())))
                    .serviceUsage(teacherSignupRequest.getServiceUsage())
                    .build();

            Teacher newTeacher = teacherRepository.save(teacher);

            OrganizationResponse organizationResponse = new OrganizationResponse(
                    newTeacher.getOrganization().getId(),
                    newTeacher.getOrganization().getName(),
                    newTeacher.getOrganization().getRegisteredAt(),
                    newTeacher.getOrganization().getType()
            );

            return new TeacherResponse(newTeacher.getId(), newTeacher.getEmail(), newTeacher.getName(), organizationResponse);



        } catch (DataIntegrityViolationException e) {
            throw new CustomException(SERVER_ERROR, e.getMessage());
        }
    }


    public LoginResponse login(TeacherLoginRequest teacherLoginRequest) throws CustomException {
        Teacher teacher = teacherRepository.findByEmailAndActiveTrue(
                        teacherLoginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND, "No such teacher with email " + teacherLoginRequest.getEmail()));

        try {
            TokenResponse tokenResponse = StudentService.getAuthentication(UserRole.TEACHER, teacher.getId(), teacherLoginRequest.getPassword(), authenticationManagerBuilder, tokenProvider, redisTemplate, refreshExpired);

            return new LoginResponse(
                    "TEACHER",
                    tokenResponse.getAccessToken(),
                    tokenResponse.getRefreshToken()
            );
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorStatus.UNAUTHORIZED_INFO, "Invalid email or password");
        }
    }


    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        SecurityUtils.checkCurrentUser(passwordResetRequest.getUserId());

        Teacher teacher = teacherRepository.findById(passwordResetRequest.getUserId())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND, "No such teacher with id " + passwordResetRequest.getUserId()));

        if (passwordEncoder.matches(passwordResetRequest.getOldPassword(), teacher.getPassword())) {
            teacher.updatePassword(passwordResetRequest.getNewPassword(), passwordEncoder);
            teacherRepository.save(teacher);
        }
        else {
            throw new CustomException(UNAUTHORIZED_INFO, "Old password not match");
        }
    }


    @Transactional
    public StudentsRegisterResponse registerStudents(StudentsRegisterRequest request) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND, "Invalid group ID: " + request.getGroupId()));

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

    @Transactional
    public void updateProfilePicture(ProfilePictureUpdateRequest profilePictureUpdateRequest) {
        SecurityUtils.checkCurrentUser(profilePictureUpdateRequest.getUserId());

        Teacher teacher = teacherRepository.findById(profilePictureUpdateRequest.getUserId())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND, "Teacher not found"));

        teacher.updateProfileUrl(profilePictureUpdateRequest.getProfilePictureUrl());
        teacherRepository.save(teacher);
    }
}

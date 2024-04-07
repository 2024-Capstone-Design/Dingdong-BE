package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.organization.OrganizationRepository;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    private final StudentRepository studentRepository;

    public TeacherSignupResponse teacherSignup(TeacherSignupRequest teacherSignupRequest) throws CustomException {
        try {

            String username = teacherSignupRequest.getEmail();

            if (teacherRepository.findByEmailAndActiveTrue(username).isPresent()) {
                throw new CustomException(DUPLICATED_MEMBER, "User already exists as teacher with username : " + teacherSignupRequest.getEmail());
            }
            if (studentRepository.findByUsernameAndActiveTrue(username).isPresent()) {
                throw new CustomException(DUPLICATED_MEMBER, "User already exists as student with username : " + teacherSignupRequest.getEmail());
            }

            Teacher teacher = Teacher.builder()
                    .email(teacherSignupRequest.getEmail())
                    .password(passwordEncoder.encode(teacherSignupRequest.getPassword()))
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .name(teacherSignupRequest.getName())
                    .organization(organizationRepository.findById(teacherSignupRequest.getOrganizationId())
                            .orElseThrow(() -> new CustomException(ORGAN_NOT_FOUND, "No such organization")))
                    .build();

            Teacher newTeacher = teacherRepository.save(teacher);

            return new TeacherSignupResponse(newTeacher.getId(), newTeacher.getEmail(), newTeacher.getName(), newTeacher.getOrganization());

        } catch (DataIntegrityViolationException e) {
            throw new CustomException(SERVER_ERROR, e.getMessage());
        }
    }

}

package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.user.student.dto.PasswordResetRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentsRegisterRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentsRegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid TeacherLoginRequest teacherLoginRequest) {
        return teacherService.login(teacherLoginRequest);
    }
    @PostMapping("/signup")
    public TeacherSignupResponse teacherSignup(@RequestBody @Valid TeacherSignupRequest teacherSignupRequest) {
        return teacherService.teacherSignup(teacherSignupRequest);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        teacherService.resetPassword(passwordResetRequest);
        return "비밀번호 변경 성공!";
    }

    @PostMapping("/register-students")
    public StudentsRegisterResponse registerStudents(@RequestBody @Valid StudentsRegisterRequest request) {
        StudentsRegisterResponse response = teacherService.registerStudents(request);
        return response;
    }


}

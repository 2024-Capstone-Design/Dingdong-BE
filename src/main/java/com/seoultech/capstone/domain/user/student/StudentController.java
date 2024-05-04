package com.seoultech.capstone.domain.user.student;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.user.student.dto.PasswordResetRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentLoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid StudentLoginRequest studentLoginRequest) {
        return studentService.login(studentLoginRequest);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        studentService.resetPassword(passwordResetRequest);
        return "비밀번호 변경 성공!";
    }

}

package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
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
    public LoginResponse login(@RequestBody TeacherLoginRequest teacherLoginRequest) {
        return teacherService.login(teacherLoginRequest);
    }
    @PostMapping("/signup")
    public TeacherSignupResponse teacherSignup(@RequestBody TeacherSignupRequest teacherSignupRequest) {
        return teacherService.teacherSignup(teacherSignupRequest);
    }

}

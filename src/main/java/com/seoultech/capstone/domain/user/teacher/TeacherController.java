package com.seoultech.capstone.domain.user.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/signup")
    public TeacherSignupResponse teacherSignup(@RequestBody TeacherSignupRequest teacherSignupRequest) {
        return teacherService.teacherSignup(teacherSignupRequest);
    }

}
package com.seoultech.capstone.domain.user.student;

import com.seoultech.capstone.domain.user.student.dto.StudentsSignupRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentsSignupResponse;
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

    @PostMapping("/signup")
    public StudentsSignupResponse signupStudents(@RequestBody StudentsSignupRequest request) {
        StudentsSignupResponse response = studentService.signupStudents(request);
        return response;
    }

}

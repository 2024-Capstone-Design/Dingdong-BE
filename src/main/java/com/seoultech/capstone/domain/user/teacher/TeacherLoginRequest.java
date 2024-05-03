package com.seoultech.capstone.domain.user.teacher;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherLoginRequest {
    private String email;
    private String password;
}

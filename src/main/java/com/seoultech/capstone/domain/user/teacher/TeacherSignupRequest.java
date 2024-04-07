package com.seoultech.capstone.domain.user.teacher;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherSignupRequest {

    private String email;
    private String password;
    private String name;
    private Integer organizationId;

}
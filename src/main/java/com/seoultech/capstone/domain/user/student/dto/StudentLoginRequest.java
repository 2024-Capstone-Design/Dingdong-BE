package com.seoultech.capstone.domain.user.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentLoginRequest {
    private Integer groupId;
    private String username;
    private String password;
}

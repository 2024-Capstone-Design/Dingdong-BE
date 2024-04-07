package com.seoultech.capstone.domain.user.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StudentSignupResponse {
    private Integer id;
    private String username;
    private LocalDate birth;

    public StudentSignupResponse(Integer id, String username, LocalDate birth) {
        this.id = id;
        this.username = username;
        this.birth = birth;
    }
}
package com.seoultech.capstone.domain.user.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StudentSignupRequest {
    private String name;
    private LocalDate birth;

}
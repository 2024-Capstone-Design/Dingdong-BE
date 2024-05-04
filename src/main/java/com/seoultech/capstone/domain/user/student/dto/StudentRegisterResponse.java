package com.seoultech.capstone.domain.user.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegisterResponse {
    private Integer id;
    private String username;
    private LocalDate birth;
}
package com.seoultech.capstone.domain.user.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsRegisterResponse {
    private int groupId;
    private List<StudentRegisterResponse> studentRegisterResponses;
}
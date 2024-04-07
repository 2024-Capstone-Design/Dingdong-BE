package com.seoultech.capstone.domain.user.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentsSignupResponse {
    private int groupId;
    private List<StudentSignupResponse> studentSignupResponses;

    public StudentsSignupResponse(int groupId, List<StudentSignupResponse> studentSignupResponses) {
        this.groupId = groupId;
        this.studentSignupResponses = studentSignupResponses;
    }
}
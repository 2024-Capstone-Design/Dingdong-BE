package com.seoultech.capstone.domain.user.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentsSignupRequest {
    private int groupId;
    private List<StudentSignupRequest> studentSignupRequests;

}
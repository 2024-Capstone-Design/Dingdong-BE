package com.seoultech.capstone.domain.user.student.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentsRegisterRequest {

    @NotNull(message = "group id는 필수 입력 값입니다.")
    private int groupId;
    private List<StudentRegisterRequest> studentRegisterRequests;

}
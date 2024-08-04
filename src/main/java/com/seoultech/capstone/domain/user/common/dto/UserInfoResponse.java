package com.seoultech.capstone.domain.user.common.dto;

import com.seoultech.capstone.domain.user.student.dto.StudentResponse;
import com.seoultech.capstone.domain.user.teacher.TeacherResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    @Schema(description = "유저 역할", example = "TEACHER")
    private String role;

    @Schema(description = "학생 정보", example = "StudentResponse 객체")
    private StudentResponse student;

    @Schema(description = "교사 정보", example = "TeacherResponse 객체")
    private TeacherResponse teacher;
}

package com.seoultech.capstone.domain.user.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentLoginRequest {

    @NotNull(message = "group id는 필수 입력 값입니다.")
    private Integer groupId;

    @NotBlank(message = "username는 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "password는 필수 입력 값입니다.")

    private String password;
}

package com.seoultech.capstone.domain.user.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentLoginRequest {

    @NotNull(message = "group id는 필수 입력 값입니다.")
    @Schema(description = "그룹 ID", example = "1")
    private Integer groupId;

    @NotBlank(message = "username는 필수 입력 값입니다.")
    @Schema(description = "사용자 이름", example = "테스트_011223")
    private String username;

    @NotBlank(message = "password는 필수 입력 값입니다.")
    @Schema(description = "비밀번호", example = "011223")
    private String password;
}

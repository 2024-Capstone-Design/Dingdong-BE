package com.seoultech.capstone.domain.user.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherLoginRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Schema(description = "교사 이메일", example = "teacher@example.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Schema(description = "비밀번호", example = "5678")
    private String password;
}
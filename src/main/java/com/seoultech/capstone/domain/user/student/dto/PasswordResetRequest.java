package com.seoultech.capstone.domain.user.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordResetRequest {

    @NotNull(message = "userId는 필수 입력 값입니다.")
    @Schema(description = "사용자 ID", example = "1")
    private Integer userId;

    @NotBlank(message = "oldPassword는 필수 입력 값입니다.")
    @Schema(description = "기존 비밀번호", example = "oldpassword123")
    private String oldPassword;

    @NotBlank(message = "newPassword는 필수 입력 값입니다.")
    @Schema(description = "새 비밀번호", example = "newpassword123")
    private String newPassword;
}

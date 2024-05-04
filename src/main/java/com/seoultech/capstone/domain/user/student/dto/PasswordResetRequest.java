package com.seoultech.capstone.domain.user.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordResetRequest {

    @NotNull(message = "userId는 필수 입력 값입니다.")
    private Integer userId;
    @NotBlank(message = "oldPassword 필수 입력 값입니다.")
    private String oldPassword;
    @NotBlank(message = "newPassword 필수 입력 값입니다.")

    private String newPassword;
}

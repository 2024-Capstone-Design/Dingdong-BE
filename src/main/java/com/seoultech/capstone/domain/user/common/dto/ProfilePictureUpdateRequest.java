package com.seoultech.capstone.domain.user.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfilePictureUpdateRequest {

    @NotNull(message = "userId는 필수 입력 값입니다.")
    @Schema(description = "사용자 ID", example = "1")
    private Integer userId;

    @Schema(description = "프로필 이미지 URL", example = "http://example.com/new-profile.jpg")
    @NotBlank(message = "프로필 이미지 URL을 입력해 주세요.")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "유효한 URL을 입력해 주세요.")
    private String profilePictureUrl;
}
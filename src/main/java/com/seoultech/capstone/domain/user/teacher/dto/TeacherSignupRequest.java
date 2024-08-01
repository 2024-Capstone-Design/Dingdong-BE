package com.seoultech.capstone.domain.user.teacher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherSignupRequest {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2~10자리여야 합니다.")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Schema(description = "교사 이름", example = "홍길동")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Schema(description = "교사 이메일", example = "teacher@example.com")
    private String email;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private String phoneNumber;

    @NotNull(message = "조직 ID는 필수 입력 값입니다.")
    @Schema(description = "조직 ID", example = "1")
    private Integer organizationId;

    @Schema(description = "서비스 이용 여부", example = "true")
    private String serviceUsage;
}

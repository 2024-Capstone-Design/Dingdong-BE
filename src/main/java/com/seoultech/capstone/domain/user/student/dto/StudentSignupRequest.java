package com.seoultech.capstone.domain.user.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StudentSignupRequest {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2~10자리여야 합니다.")
    @NotBlank(message = "name은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "birth는 필수 입력 값입니다.")
    private LocalDate birth;

}
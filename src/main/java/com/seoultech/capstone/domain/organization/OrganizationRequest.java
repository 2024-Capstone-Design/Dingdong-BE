package com.seoultech.capstone.domain.organization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequest {

    @Schema(description = "조직 이름", example = "Example Organization")
    private String name;

    @Schema(description = "등록 일자", example = "2023-08-01T00:00:00")
    private LocalDateTime registeredAt;

    @Schema(description = "조직 유형", example = "학교")
    private String type;

    @Schema(description = "연락처 정보", example = "contact@example.com")
    private String contactInfo;

    @Schema(description = "관리자 정보", example = "admin@example.com")
    private String adminInfo;
}


package com.seoultech.capstone.domain.user.teacher.dto;

import com.seoultech.capstone.domain.organization.OrganizationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherResponse {

    @Schema(description = "교사 ID", example = "1")
    private int id;

    @Schema(description = "교사 이메일", example = "teacher@example.com")
    private String email;

    @Schema(description = "교사 이름", example = "홍길동")
    private String name;

    @Schema(description = "조직 정보", example = "홍길동")
    private OrganizationResponse organizationResponse;

    public TeacherResponse(int id, String email, String name, OrganizationResponse organizationResponse) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.organizationResponse = organizationResponse;
    }
}

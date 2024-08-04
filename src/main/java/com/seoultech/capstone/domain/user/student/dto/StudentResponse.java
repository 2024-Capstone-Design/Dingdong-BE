package com.seoultech.capstone.domain.user.student.dto;

import com.seoultech.capstone.domain.group.GroupResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    @Schema(description = "학생 ID", example = "1")
    private int id;

    @Schema(description = "학생 사용자 이름", example = "student01")
    private String username;

    @Schema(description = "학생 이름", example = "홍길동")
    private String name;

    @Schema(description = "생일", example = "2000-01-01")
    private LocalDate birth;

    @Schema(description = "반 정보")
    private GroupResponse group;

    @Schema(description = "프로필 URL", example = "http://example.com/profile.jpg")
    private String profileUrl;

    @Schema(description = "마지막 접근 시간", example = "2023-08-01T00:00:00")
    private LocalDateTime lastAccessedAt;

    @Schema(description = "활성 상태", example = "true")
    private Boolean active;
}


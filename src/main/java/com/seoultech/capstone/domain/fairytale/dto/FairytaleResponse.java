package com.seoultech.capstone.domain.fairytale.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FairytaleResponse {

    @Schema(description = "동화 ID", example = "1")
    private Integer id;

    @Schema(description = "동화 제목", example = "신데렐라")
    private String title;

    @Schema(description = "동화 배경", example = "옛날 옛적에...")
    private String background;

    @Schema(description = "동화 등장인물", example = "신데렐라, 왕자, 계모, 의붓자매")
    private String characters;

    @Schema(description = "동화 내용", example = "한 소녀가...")
    private String content;

    @Schema(description = "선생님 이름", example = "홍길동")
    private String teacherName;

    @Schema(description = "생성 날짜", example = "2023-07-21T15:30:00")
    private LocalDateTime createdAt;

    @Builder
    public FairytaleResponse(Integer id, String title, String background, String characters, String content, String teacherName, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.background = background;
        this.characters = characters;
        this.content = content;
        this.teacherName = teacherName;
        this.createdAt = createdAt;
    }
}



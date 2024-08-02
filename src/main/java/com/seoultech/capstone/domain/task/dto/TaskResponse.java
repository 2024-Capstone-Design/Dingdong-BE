package com.seoultech.capstone.domain.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    @Schema(description = "과제 ID", example = "1")
    private Integer taskId;

    @Schema(description = "과제 제목", example = "신데렐라 결말 바꾸기")
    private String taskTitle;

    @Schema(description = "과제 요약", example = "신데렐라 결말을 내가 원하는 방향으로 바꾸어 보세요. ")
    private String taskSummary;

    @Schema(description = "동화 ID", example = "1")
    private Integer fairytaleId;

    @Schema(description = "동화 제목", example = "신데렐라")
    private String fairytaleTitle;

    @Schema(description = "동화 이미지 URL", example = "http://example.com/image.jpg")
    private String fairytaleImageUrl;

    @Schema(description = "목표 반 ID", example = "1")
    private Integer targetClassId;

    @Schema(description = "목표 반 이름", example = "1")
    private String targetClassName;

    @Schema(description = "시작 날짜", example = "2023-08-01T00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "종료 날짜", example = "2023-08-31T23:59:59")
    private LocalDateTime finishDate;

    @Schema(description = "질문 ID", example = "1")
    private Integer questionId;

    @Schema(description = "질문 타입", example = "등장인물 성격 바꾸기")
    private String questionType;

    @Schema(description = "질문 프롬프트", example = "등장인물의 성격을 바줘라.")
    private String questionPrompt;

    @Schema(description = "질문 output")
    private String questionOutput;

    @Schema(description = "생성 일자", example = "2023-08-01T00:00:00")
    private LocalDateTime createdAt;

}


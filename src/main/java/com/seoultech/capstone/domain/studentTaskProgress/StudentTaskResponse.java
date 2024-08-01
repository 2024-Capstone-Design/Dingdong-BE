package com.seoultech.capstone.domain.studentTaskProgress;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StudentTaskResponse {

    @Schema(description = "과제 ID", example = "1")
    private Integer taskId;

    @Schema(description = "동화 ID", example = "1")
    private Integer fairytaleId;

    @Schema(description = "목표 반 ID", example = "1")
    private Integer targetClassId;

    @Schema(description = "교사 ID", example = "1")
    private Integer teacherId;

    @Schema(description = "과제 제목", example = "신데렐라 읽기")
    private String title;

    @Schema(description = "과제 요약", example = "신데렐라를 읽고 요약하기")
    private String summary;

    @Schema(description = "시작 날짜", example = "2023-08-01T00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "종료 날짜", example = "2023-08-31T23:59:59")
    private LocalDateTime finishDate;

    @Schema(description = "질문 ID", example = "1")
    private Integer questionId;

    @Schema(description = "생성 일자", example = "2023-08-01T00:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "학생 ID", example = "1")
    private Integer studentId;

    @Schema(description = "완료 여부", example = "true")
    private Boolean completed;

    @Schema(description = "완료 날짜", example = "2023-08-15T00:00:00")
    private LocalDateTime completionDate;

    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
    private String baseImgUrl;

    @Builder
    public StudentTaskResponse(Integer taskId, Integer fairytaleId, Integer targetClassId, Integer teacherId, String title, String summary, LocalDateTime startDate, LocalDateTime finishDate, Integer questionId, LocalDateTime createdAt, Integer studentId, Boolean completed, LocalDateTime completionDate, String baseImgUrl) {
        this.taskId = taskId;
        this.fairytaleId = fairytaleId;
        this.targetClassId = targetClassId;
        this.teacherId = teacherId;
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.questionId = questionId;
        this.createdAt = createdAt;
        this.studentId = studentId;
        this.completed = completed;
        this.completionDate = completionDate;
        this.baseImgUrl = baseImgUrl;
    }
}

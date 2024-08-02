package com.seoultech.capstone.domain.studentTaskProgress.dto;

import com.seoultech.capstone.domain.studentTaskProgress.Enum.Progress;
import com.seoultech.capstone.domain.task.dto.TaskResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentTaskProgressResponse extends TaskResponse {

    @Schema(description = "학생 과제 진행 ID", example = "1")
    private Integer studentTaskProgressId;

    @Schema(description = "학생 ID", example = "1")
    private Integer studentId;

    @Schema(description = "완료 여부", example = "true")
    private Boolean completed;

    @Schema(description = "완료 날짜", example = "2023-08-15T00:00:00")
    private LocalDateTime completionDate;

    @Schema(description = "진행 상태", example = "NOT_STARTED", allowableValues = {"NOT_STARTED", "CHAT", "SKETCH", "CODING", "COMPLETED"})
    private Progress progress;

    @Builder(builderMethodName = "studentTaskProgressResponseBuilder")
    public StudentTaskProgressResponse(Integer studentTaskProgressId, Integer taskId, String taskTitle, String taskSummary, Integer fairytaleId, String fairytaleTitle, String fairytaleImageUrl, Integer targetClassId, String targetClassName, LocalDateTime startDate, LocalDateTime finishDate, Integer questionId, String questionType, String questionPrompt, String questionOutput, LocalDateTime createdAt, Integer studentId, Boolean completed, LocalDateTime completionDate, Progress progress) {
        super(taskId, taskTitle, taskSummary, fairytaleId, fairytaleTitle, fairytaleImageUrl, targetClassId, targetClassName, startDate, finishDate, questionId, questionType, questionPrompt, questionOutput, createdAt);
        this.studentTaskProgressId = studentTaskProgressId;
        this.studentId = studentId;
        this.completed = completed;
        this.completionDate = completionDate;
        this.progress = progress;
    }
}

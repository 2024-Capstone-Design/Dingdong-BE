package com.seoultech.capstone.domain.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentTaskResponse extends TaskResponse {

    @Schema(description = "학생 ID", example = "1")
    private Integer studentId;

    @Schema(description = "학생 과제 완료 여부", example = "true")
    private Boolean completed;

    @Builder(builderMethodName = "studentTaskResponseBuilder")
    public StudentTaskResponse(Integer taskId, String taskTitle, String taskSummary, Integer fairytaleId, String fairytaleTitle, String fairytaleImageUrl, Integer targetClassId, String targetClassName, LocalDateTime startDate, LocalDateTime finishDate, Integer questionId, String questionType, String questionPrompt, String questionOutput, LocalDateTime createdAt, Integer studentId, Boolean completed) {
        super(taskId, taskTitle, taskSummary, fairytaleId, fairytaleTitle, fairytaleImageUrl, targetClassId, targetClassName, startDate, finishDate, questionId, questionType, questionPrompt, questionOutput, createdAt);
        this.studentId = studentId;
        this.completed = completed;
    }
}


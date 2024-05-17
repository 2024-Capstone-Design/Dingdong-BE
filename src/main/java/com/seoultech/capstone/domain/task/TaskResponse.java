package com.seoultech.capstone.domain.task;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {
    private Integer id;
    private Integer fairytaleId;
    private Integer targetClassId;
    private Integer teacherId;
    private String title;
    private String summary;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private Integer questionId;
    private LocalDateTime createdAt;

    @Builder
    public TaskResponse(Integer id, Integer fairytaleId, Integer targetClassId, Integer teacherId, String title, String summary, LocalDateTime startDate, LocalDateTime finishDate, Integer questionId, LocalDateTime createdAt) {
        this.id = id;
        this.fairytaleId = fairytaleId;
        this.targetClassId = targetClassId;
        this.teacherId = teacherId;
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.questionId = questionId;
        this.createdAt = createdAt;
    }
}

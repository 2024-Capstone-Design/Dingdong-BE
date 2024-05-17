package com.seoultech.capstone.domain.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {
    private Integer fairytaleId;
    private Integer targetClassId;
    private String title;
    private String summary;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private Integer questionId;
}


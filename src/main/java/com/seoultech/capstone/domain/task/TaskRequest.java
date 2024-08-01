package com.seoultech.capstone.domain.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {

    @Schema(description = "동화 ID", example = "1")
    private Integer fairytaleId;

    @Schema(description = "목표 반 ID", example = "1")
    private Integer targetClassId;

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
}

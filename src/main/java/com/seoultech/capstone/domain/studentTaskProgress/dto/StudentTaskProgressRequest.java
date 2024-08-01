package com.seoultech.capstone.domain.studentTaskProgress.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentTaskProgressRequest {

    @Schema(description = "학생 ID", example = "1")
    private Integer studentId;

    @Schema(description = "과제 ID", example = "1")
    private Integer taskId;

}

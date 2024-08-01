package com.seoultech.capstone.domain.studentTaskProgress.Enum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "학생 과제 진행 상태")
public enum Progress {
    NOT_STARTED,
    CHAT,
    SKETCH,
    CODING,
    COMPLETED
}

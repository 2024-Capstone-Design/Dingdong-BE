package com.seoultech.capstone.domain.studentTaskProgress.dto;

import com.seoultech.capstone.domain.studentTaskProgress.Enum.Progress;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentTaskProgressUpdateRequest {

    @Schema(description = "진행 상태", example = "CHAT", allowableValues = {"NOT_STARTED", "CHAT", "SKETCH", "CODING", "COMPLETED"})
    @NotNull(message = "진행 상태는 필수 입력 값입니다.")
    private Progress progress;
}

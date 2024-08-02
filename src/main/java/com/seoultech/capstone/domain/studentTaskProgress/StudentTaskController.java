package com.seoultech.capstone.domain.studentTaskProgress;

import com.seoultech.capstone.common.ListWrapper;
import com.seoultech.capstone.domain.studentTaskProgress.Enum.Progress;
import com.seoultech.capstone.domain.studentTaskProgress.dto.StudentTaskProgressRequest;
import com.seoultech.capstone.domain.studentTaskProgress.dto.StudentTaskProgressResponse;
import com.seoultech.capstone.domain.studentTaskProgress.dto.StudentTaskProgressUpdateRequest;
import com.seoultech.capstone.response.ApiResponseDTO;
import com.seoultech.capstone.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "학생 과제 진행 관련 API", description = "학생 과제 진행과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/student-task-progress")
@RequiredArgsConstructor
public class StudentTaskController {

    private final StudentTaskService studentTaskService;

    @Operation(summary = "학생 과제 진행 시작", description = "학생이 과제 진행을 시작합니다.")
    @ApiResponse(responseCode = "201", description = "학생 과제 진행 시작 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @PostMapping
    public ResponseEntity<ApiResponseDTO<StudentTaskProgressResponse>> createStudentTaskProgress(@RequestBody @Valid StudentTaskProgressRequest request) {
        StudentTaskProgressResponse response = studentTaskService.createStudentTaskProgress(request);
        return ApiResponseDTO.success(SuccessStatus.STUDENT_TASK_PROGRESS_ADD_SUCCESS, response);
    }

    @Operation(summary = "학생별 진행중 과제 조회", description = "학생 ID로 진행중인/완료된 모든 과제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학생별 진행중 과제 조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponseDTO<ListWrapper<StudentTaskProgressResponse>>> getProgressedTasksByStudentId(
            @PathVariable Integer studentId,
            @Parameter(
                    description = "정렬 기준 (completionDate 또는 progress)",
                    example = "completionDate",
                    schema = @Schema(allowableValues = {"completionDate", "progress"})
            )
            @RequestParam(required = false) String sortBy,
            @Parameter(
                    description = "포함할 진행 상태",
                    array = @ArraySchema(schema = @Schema(implementation = Progress.class))
            )
            @RequestParam(required = false) List<Progress> includeProgress) {
        List<StudentTaskProgressResponse> tasks = studentTaskService.getProgressedTasksByStudentId(studentId, sortBy, includeProgress);
        return ApiResponseDTO.success(SuccessStatus.TASK_RETRIEVAL_SUCCESS, new ListWrapper<>(tasks));
    }


    @Operation(
            summary = "학생 과제 진행 상태 업데이트", description = "studentTaskId로 학생의 과제 진행 상태를 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "학생 과제 진행 상태 업데이트 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @PatchMapping("/{id}/progress")
    public ResponseEntity<ApiResponseDTO<StudentTaskProgressResponse>> updateProgress(@PathVariable Integer id, @RequestBody @Valid StudentTaskProgressUpdateRequest request) {
        StudentTaskProgressResponse response = studentTaskService.updateProgress(id, request.getProgress());
        return ApiResponseDTO.success(SuccessStatus.STUDENT_TASK_PROGRESS_UPDATE_SUCCESS, response);
    }
}

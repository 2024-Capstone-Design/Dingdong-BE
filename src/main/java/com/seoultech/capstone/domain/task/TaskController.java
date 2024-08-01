package com.seoultech.capstone.domain.task;

import com.seoultech.capstone.common.ListWrapper;
import com.seoultech.capstone.domain.studentTaskProgress.StudentTaskResponse;
import com.seoultech.capstone.response.ApiResponseDTO;
import com.seoultech.capstone.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "과제 관리 API", description = "과제와 관련된 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "과제 추가", description = "새로운 과제를 추가합니다.")
    @ApiResponse(responseCode = "201", description = "과제 추가 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @Secured("ROLE_TEACHER")
    @PostMapping
    public ResponseEntity<ApiResponseDTO<TaskResponse>> addTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse createdTask = taskService.addTask(taskRequest);
        return ApiResponseDTO.success(SuccessStatus.TASK_ADD_SUCCESS, createdTask);
    }

    @Operation(summary = "교사별 과제 조회", description = "교사 ID로 연관된 모든 과제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "과제 조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponseDTO<ListWrapper<TaskResponse>>> getTasksByTeacherId(@PathVariable Integer teacherId) {
        List<TaskResponse> tasks = taskService.getTasksByTeacherId(teacherId);
        return ApiResponseDTO.success(SuccessStatus.TASK_RETRIEVAL_SUCCESS, new ListWrapper<>(tasks));
    }

    @Operation(summary = "학생별 배정된 과제 조회", description = "학생 ID로 배정된 모든 과제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "과제 조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponseDTO<ListWrapper<TaskResponse>>> getTasksByStudentId(@PathVariable Integer studentId) {
        List<TaskResponse> tasks = taskService.getTasksByStudentId(studentId);
        return ApiResponseDTO.success(SuccessStatus.TASK_RETRIEVAL_SUCCESS, new ListWrapper<>(tasks));
    }

    @Operation(summary = "학생별 진행중 과제 조회", description = "학생 ID로 진행중인/완료된 모든 과제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "과제 조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @GetMapping("/student/{studentId}/progress")
    public ResponseEntity<ApiResponseDTO<ListWrapper<StudentTaskResponse>>> getProgressedTasksByStudentId(@PathVariable Integer studentId) {
        List<StudentTaskResponse> tasks = taskService.getProgressedTasksByStudentId(studentId);
        return ApiResponseDTO.success(SuccessStatus.TASK_RETRIEVAL_SUCCESS, new ListWrapper<>(tasks));
    }
}

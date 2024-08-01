package com.seoultech.capstone.domain.group;

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
import org.springframework.web.bind.annotation.*;

@Tag(name = "그룹 관리 API", description = "그룹과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "그룹 생성", description = "새로운 그룹을 생성합니다.")
    @ApiResponse(responseCode = "201", description = "그룹 생성 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @PostMapping
    public ResponseEntity<ApiResponseDTO<GroupResponse>> createGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse createdGroup = groupService.createGroup(groupRequest);
        return ApiResponseDTO.success(SuccessStatus.GROUP_CREATE_SUCCESS, createdGroup);
    }
}

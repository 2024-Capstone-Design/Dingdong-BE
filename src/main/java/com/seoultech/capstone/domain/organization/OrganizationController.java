package com.seoultech.capstone.domain.organization;

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

import java.util.List;

@Tag(name = "조직 관리 API", description = "조직과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/org")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @Operation(summary = "조직 조회", description = "모든 조직을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조직 조회 성공", content = @Content(schema = @Schema(implementation = OrganizationResponse.class)))
    @GetMapping("")
    public ResponseEntity<ApiResponseDTO<List<OrganizationResponse>>> getOrganizations() {
        List<OrganizationResponse> organizations = organizationService.getOrganizations();
        return ApiResponseDTO.success(SuccessStatus.ORGANIZATION_RETRIEVAL_SUCCESS, organizations);
    }
}


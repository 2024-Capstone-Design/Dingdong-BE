package com.seoultech.capstone.domain.fairytale.controller;

import com.seoultech.capstone.common.ListWrapper;
import com.seoultech.capstone.domain.fairytale.dto.FairytaleRequest;
import com.seoultech.capstone.domain.fairytale.dto.FairytaleResponse;
import com.seoultech.capstone.domain.fairytale.service.FairytaleService;
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

@Tag(name = "동화 관련 API", description = "동화와 관련된 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fairytale")
public class FairytaleController {

    private final FairytaleService fairytaleService;

    @Operation(summary = "동화 추가", description = "새로운 동화를 추가합니다.")
    @ApiResponse(responseCode = "201", description = "동화 추가 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @Secured("ROLE_TEACHER")
    @PostMapping
    public ResponseEntity<ApiResponseDTO<FairytaleResponse>> addFairytale(@RequestBody FairytaleRequest fairytaleRequest) {
        FairytaleResponse createdFairytale = fairytaleService.addFairytale(fairytaleRequest);
        return ApiResponseDTO.success(SuccessStatus.FAIRYTALE_ADD_SUCCESS, createdFairytale);
    }

    @Operation(summary = "동화 리스트 조회", description = "전체 동화 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "동화 리스트 조회 성공", content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    @GetMapping
    public ResponseEntity<ApiResponseDTO<ListWrapper<FairytaleResponse>>> getAllFairytales() {
        List<FairytaleResponse> fairytales = fairytaleService.getAllFairytales();
        return ApiResponseDTO.success(SuccessStatus.FAIRYTALE_LIST_RETRIEVAL_SUCCESS, new ListWrapper<>(fairytales));
    }

    @Operation(summary = "동화 조회", description = "ID로 동화를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "동화 조회 성공", content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<FairytaleResponse>> getFairytaleById(@PathVariable Integer id) {
        FairytaleResponse fairytale = fairytaleService.getFairytaleById(id);
        return ApiResponseDTO.success(SuccessStatus.FAIRYTALE_RETRIEVAL_SUCCESS, fairytale);
    }
}

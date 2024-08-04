package com.seoultech.capstone.domain.user.common.controller;

import com.seoultech.capstone.domain.user.common.UserService;
import com.seoultech.capstone.domain.user.common.dto.UserInfoResponse;
import com.seoultech.capstone.response.ApiResponseDTO;
import com.seoultech.capstone.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "3. 유저 공통 API", description = "사용자 관리에 공통적으로 사용되는 API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "현재 유저 정보 가져오기", description = "토큰을 기반으로 현재 인증된 유저의 정보를 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserInfoResponse.class)))
    @GetMapping("/current")
    public ResponseEntity<ApiResponseDTO<UserInfoResponse>> getCurrentUserInfo() {
        UserInfoResponse userInfoResponse = userService.getCurrentUserInfo();
        return ApiResponseDTO.success(SuccessStatus.USER_INFO_GET_SUCCESS, userInfoResponse);
    }
}


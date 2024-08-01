package com.seoultech.capstone.domain.auth.controller;

import com.seoultech.capstone.domain.auth.jwt.TokenResponse;
import com.seoultech.capstone.domain.auth.service.AuthService;
import com.seoultech.capstone.response.ApiResponseDTO;
import com.seoultech.capstone.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3. 인증 관련 API", description = "인증과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Logout", description = "현재 사용자를 로그아웃 합니다.")
    @ApiResponse(responseCode = "200", description = "Logout successful", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ApiResponseDTO.success(SuccessStatus.LOGOUT_SUCCESS, "logout success");
    }

    @Operation(summary = "Token Refresh", description = "만료된 JWT 토큰을 리프레쉬 합니다.")
    @ApiResponse(responseCode = "200", description = "Token refresh successful", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    @GetMapping(value = "/tokenRefresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDTO<TokenResponse>> refresh() {
        TokenResponse tokenResponse = authService.refreshToken();
        return ApiResponseDTO.success(SuccessStatus.TOKEN_REFRESH_SUCCESS, tokenResponse);
    }
}

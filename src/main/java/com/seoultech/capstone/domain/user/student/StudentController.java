package com.seoultech.capstone.domain.user.student;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.user.student.dto.PasswordResetRequest;
import com.seoultech.capstone.domain.user.common.dto.ProfilePictureUpdateRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentLoginRequest;
import com.seoultech.capstone.response.ApiResponseDTO;
import com.seoultech.capstone.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2. 학생 관리 API", description = "학생과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "학생 로그인", description = "학생이 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(@RequestBody @Valid StudentLoginRequest studentLoginRequest) {
        LoginResponse loginResponse = studentService.login(studentLoginRequest);
        return ApiResponseDTO.success(SuccessStatus.LOGIN_SUCCESS, loginResponse);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "비밀번호 재설정", description = "학생이 비밀번호를 재설정합니다.")
    @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDTO<String>> resetPassword(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        studentService.resetPassword(passwordResetRequest);
        return ApiResponseDTO.success(SuccessStatus.PASSWORD_RESET_SUCCESS, "비밀번호 변경 성공!");
    }

    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "프로필 사진 수정", description = "학생이 프로필 사진을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "프로필 사진 수정 성공", content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    @PostMapping("/update-profile-picture")
    public ResponseEntity<ApiResponseDTO<String>> updateProfilePicture(@RequestBody @Valid ProfilePictureUpdateRequest profilePictureUpdateRequest) {
        studentService.updateProfilePicture(profilePictureUpdateRequest);
        return ApiResponseDTO.success(SuccessStatus.PROFILE_PICTURE_UPDATE_SUCCESS, "프로필 사진 수정 성공!");
    }
}


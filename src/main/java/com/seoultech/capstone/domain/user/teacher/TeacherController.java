package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.auth.dto.LoginResponse;
import com.seoultech.capstone.domain.user.student.dto.PasswordResetRequest;
import com.seoultech.capstone.domain.user.common.dto.ProfilePictureUpdateRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentsRegisterRequest;
import com.seoultech.capstone.domain.user.student.dto.StudentsRegisterResponse;
import com.seoultech.capstone.domain.user.teacher.dto.TeacherSignupRequest;
import com.seoultech.capstone.domain.user.teacher.dto.TeacherResponse;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "1. 선생 관리 API", description = "선생과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(summary = "교사 회원가입", description = "새로운 교사를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = TeacherResponse.class)))
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<TeacherResponse>> teacherSignup(@RequestBody @Valid TeacherSignupRequest teacherSignupRequest) {
        TeacherResponse signupResponse = teacherService.teacherSignup(teacherSignupRequest);
        return ApiResponseDTO.success(SuccessStatus.SIGNUP_SUCCESS, signupResponse);
    }

    @Operation(summary = "교사 로그인", description = "교사가 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(@RequestBody @Valid TeacherLoginRequest teacherLoginRequest) {
        LoginResponse loginResponse = teacherService.login(teacherLoginRequest);
        return ApiResponseDTO.success(SuccessStatus.LOGIN_SUCCESS, loginResponse);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "비밀번호 재설정", description = "교사가 비밀번호를 재설정합니다.")
    @ApiResponse(responseCode = "200", description = "비밀번호 재설정 성공", content = @Content(schema = @Schema(implementation = Object.class)))
    @ApiResponse(responseCode = "401", description = "비밀번호 재설정 실패", content = @Content(schema = @Schema(implementation = Object.class)))
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDTO<Object>> resetPassword(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        teacherService.resetPassword(passwordResetRequest);
        return ApiResponseDTO.success(SuccessStatus.PASSWORD_RESET_SUCCESS);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "프로필 사진 수정", description = "교사가 프로필 사진을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "프로필 사진 수정 성공", content = @Content(schema = @Schema(implementation = Object.class)))
    @PostMapping("/update-profile-picture")
    public ResponseEntity<ApiResponseDTO<Object>> updateProfilePicture(@RequestBody @Valid ProfilePictureUpdateRequest profilePictureUpdateRequest) {
        teacherService.updateProfilePicture(profilePictureUpdateRequest);
        return ApiResponseDTO.success(SuccessStatus.PROFILE_PICTURE_UPDATE_SUCCESS);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "학생 등록", description = "교사가 학생들을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "학생 등록 성공", content = @Content(schema = @Schema(implementation = StudentsRegisterResponse.class)))
    @PostMapping("/register-students")
    public ResponseEntity<ApiResponseDTO<StudentsRegisterResponse>> registerStudents(@RequestBody @Valid StudentsRegisterRequest request) {
        StudentsRegisterResponse response = teacherService.registerStudents(request);
        return ApiResponseDTO.success(SuccessStatus.STUDENTS_REGISTER_SUCCESS, response);
    }

}

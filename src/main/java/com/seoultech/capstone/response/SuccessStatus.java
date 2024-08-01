package com.seoultech.capstone.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {

    /**
     * 인증
     */
    TOKEN_REFRESH_SUCCESS(HttpStatus.OK, "토큰 갱신 성공"),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),

    /**
     * 회원
     */
    MEMBER_INFO_RETRIEVAL_SUCCESS(OK, "회원 정보 조회 성공"),
    MEMBER_DETAILS_RETRIEVAL_SUCCESS(OK, "회원 상세 정보 조회 성공"),
    MEMBER_NICKNAME_UPDATE_SUCCESS(OK, "회원 닉네임 수정 성공"),

    /**
     * 반
     */
    GROUP_CREATE_SUCCESS(HttpStatus.CREATED, "그룹 생성 성공"),

    /**
     * 조직
     */
    ORGANIZATION_RETRIEVAL_SUCCESS(HttpStatus.OK, "조직 조회 성공"),

    /**
     * 동화
     */
    FAIRYTALE_ADD_SUCCESS(HttpStatus.CREATED, "동화 추가 성공"),

    /**
     * 학생 관련
     */
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    PASSWORD_RESET_SUCCESS(HttpStatus.OK, "비밀번호 변경 성공"),
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    STUDENTS_REGISTER_SUCCESS(HttpStatus.OK, "학생 등록 성공"),

    /**
     * 과제 관련
     */
    TASK_ADD_SUCCESS(HttpStatus.CREATED, "과제 추가 성공"),
    TASK_RETRIEVAL_SUCCESS(HttpStatus.OK, "과제 조회 성공");

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}


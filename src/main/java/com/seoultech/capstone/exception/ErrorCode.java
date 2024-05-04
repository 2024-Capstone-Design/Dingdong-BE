package com.seoultech.capstone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    //400 Bad Request: 클라이언트의 요청이 잘못되어 서버가 요청을 이해할 수 없음
    MISMATCH_INFO(BAD_REQUEST, "There is no data related to request"),
    INVALID_REQUEST(BAD_REQUEST, "Wrong request provided"),


    //401 Unauthorized: 인증되지 않은 사용자가 보호된 페이지에 접근하려고 할 때 사용
    INVALID_TOKEN(UNAUTHORIZED, "Token information is invalid"),


    //403 Forbidden: 인증된 사용자가 요청한 페이지에 접근할 권한이 없는 경우 사용
    INVALID_AUTHORITY(FORBIDDEN, "User is not authorized"),
    LIMIT_REQUEST(FORBIDDEN, "The request is limited"),

    //404 Not Found
    MEMBER_NOT_FOUND(NOT_FOUND, "User is not founded"),
    ORGAN_NOT_FOUND(NOT_FOUND, "Organization is not founded"),
    GROUP_NOT_FOUND(NOT_FOUND, "Organization is not founded"),


    //409 CONFLICT
    DUPLICATED_MEMBER(CONFLICT, "user is already existed"),
    DUPLICATED_DATA(CONFLICT, "Data already exists"),

    //500
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "Unexpected server error");


    private final HttpStatus httpStatus;
    private final String detail;
}
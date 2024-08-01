package com.seoultech.capstone.exception;

import com.seoultech.capstone.response.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorStatus errorStatus;
    private String value;
}
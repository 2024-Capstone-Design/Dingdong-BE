package com.seoultech.capstone.exception;

import com.seoultech.capstone.response.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.seoultech.capstone.response.ErrorStatus.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleCustomException(CustomException e) {
        return ApiResponseDTO.fail(e.getErrorStatus(), e.getValue());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleMethodArgumentValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        String message = fieldError.getField() + " " + fieldError.getDefaultMessage();
        return ApiResponseDTO.fail(INVALID_REQUEST, message);
    }

}
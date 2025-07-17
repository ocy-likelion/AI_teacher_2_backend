package com.ll.ilta.global.exception;

import com.ll.ilta.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = ErrorResponse.of(
            errorCode.getStatus(),
            errorCode.getCode(),
            errorCode.getMessage()
        );
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error("[AuthenticationException] message: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.of(
            HttpStatus.UNAUTHORIZED.value(),
            "AUTHENTICATION_FAILED",
            "로그인 정보가 올바르지 않습니다."
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}

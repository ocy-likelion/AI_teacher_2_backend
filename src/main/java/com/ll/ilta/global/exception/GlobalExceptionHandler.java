package com.ll.ilta.global.exception;

import com.ll.ilta.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.error("[CustomException] message: {}", ex.getMessage());
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
}

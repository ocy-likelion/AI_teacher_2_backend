package com.ll.ilta.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final  int status;
    private final  String code;
    private final  String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;

    @Builder(access = AccessLevel.PRIVATE)
    private ErrorResponse(int status, String code, String message, LocalDateTime timestamp) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ErrorResponse of(int status, String code, String message) {
        return ErrorResponse.builder()
            .status(status)
            .code(code)
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }
}

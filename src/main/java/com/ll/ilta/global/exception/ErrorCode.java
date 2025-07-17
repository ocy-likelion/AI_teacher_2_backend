package com.ll.ilta.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND_MEMBER(404, "NOT_FOUND_MEMBER", "해당 자녀가 없습니다."),

    INVALID_PASSWORD(401, "INVALID_PASSWORD", "비밀번호가 틀렸습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

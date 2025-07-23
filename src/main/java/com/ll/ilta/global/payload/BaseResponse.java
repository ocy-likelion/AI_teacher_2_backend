package com.ll.ilta.global.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ll.ilta.global.payload.code.BaseCode;
import com.ll.ilta.global.payload.code.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공한 경우 응답 생성

    public static <T> BaseResponse<T> onSuccess(T result) {
        return new BaseResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), result);
    }

    public static <T> BaseResponse<T> of(BaseCode code, T result) {
        return new BaseResponse<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(),
            result);
    }


    // 실패한 경우 응답 생성
    public static <T> BaseResponse<T> onFailure(String code, String message, T data) {
        return new BaseResponse<>(false, code, message, data);
    }
}

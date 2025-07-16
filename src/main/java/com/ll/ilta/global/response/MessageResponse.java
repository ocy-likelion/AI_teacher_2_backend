package com.ll.ilta.global.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponse {

    private final String message;

    @Builder(access = AccessLevel.PRIVATE)
    private MessageResponse(String message) {
        this.message = message;
    }

    public static MessageResponse of(String message) {
        return MessageResponse.builder()
            .message(message)
            .build();
    }
}

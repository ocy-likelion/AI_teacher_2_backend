package com.ll.ilta.domain.member.v1.dto;

import lombok.Getter;

@Getter
public class MemberLoginResponseDto {
    private String message;
    private String accessToken;

    public MemberLoginResponseDto(String message, String accessToken) {
        this.message = message;
        this.accessToken = accessToken;
    }
}

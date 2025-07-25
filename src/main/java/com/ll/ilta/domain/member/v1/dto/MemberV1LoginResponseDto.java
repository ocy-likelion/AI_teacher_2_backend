package com.ll.ilta.domain.member.v1.dto;

import lombok.Getter;

@Getter
public class MemberV1LoginResponseDto {
    private String message;
    private String accessToken;

    public MemberV1LoginResponseDto(String message, String accessToken) {
        this.message = message;
        this.accessToken = accessToken;
    }
}

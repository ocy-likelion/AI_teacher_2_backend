package com.ll.ilta.domain.member.v1.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginRequestDto {

    private final String username;
    private final String password;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberLoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static MemberLoginRequestDto of(String username, String password) {
        return MemberLoginRequestDto.builder()
            .username(username)
            .password(password)
            .build();
    }
}

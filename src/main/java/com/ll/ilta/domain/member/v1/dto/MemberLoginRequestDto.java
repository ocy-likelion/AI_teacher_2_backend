package com.ll.ilta.domain.member.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequestDto {

    private String username;
    private String password;

    public static MemberLoginRequestDto of(String username, String password) {
        return new MemberLoginRequestDto(username, password);
    }
}

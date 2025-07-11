package com.ll.ilta.domain.member.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {

    private String username;
    private String password;

    public MemberLoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}

package com.ll.ilta.domain.member.converter;

import com.ll.ilta.domain.member.entity.Member;

public class AuthConverter {

    public static Member toMember(String email, String nickname) {
        return Member.builder()
            .email(email)
            .role("ROLE_USER")
            .nickname(nickname)
            .build();
    }

}

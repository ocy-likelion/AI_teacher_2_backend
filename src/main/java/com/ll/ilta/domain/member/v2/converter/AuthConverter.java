package com.ll.ilta.domain.member.v2.converter;

import com.ll.ilta.domain.member.v2.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthConverter {

    public static Member toMember(String email, String nickname) {
        return Member.builder()
            .email(email)
            .role("ROLE_USER")
            .nickname(nickname)
            .build();
    }

}

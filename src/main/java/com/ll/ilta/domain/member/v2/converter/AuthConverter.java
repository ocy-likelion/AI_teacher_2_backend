package com.ll.ilta.domain.member.v2.converter;

import com.ll.ilta.domain.member.v2.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthConverter {

    public static Member toMember(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
        return Member.builder()
            .email(email)
            .role("ROLE_USER")
            .password(passwordEncoder.encode(password))
            .nickname(nickname)
            .build();
    }

}

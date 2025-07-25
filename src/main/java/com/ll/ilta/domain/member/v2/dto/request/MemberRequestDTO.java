package com.ll.ilta.domain.member.v2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class JoinDTO {

        private String name;
        private String email;
        private String password;
        private String role;
    }

    @Getter
    public static class UpdateMemberDTO {

        private String name;
        private Integer grade;
    }

    @Getter
    public static class LoginRequestDTO {

        private String email;
        private String password;
    }
}

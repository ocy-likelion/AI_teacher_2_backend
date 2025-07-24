package com.ll.ilta.domain.member.v2.dto.request;

import lombok.Getter;

public class MemberRequestDTO {

    @Getter
    public static class JoinDTO {

        private String name;
        private String email;
        private String password;
    }

    @Getter
    public static class UpdateUserDTO {

        private String name;
    }

    @Getter
    public static class LoginRequestDTO {

        private String email;
        private String password;
    }
}

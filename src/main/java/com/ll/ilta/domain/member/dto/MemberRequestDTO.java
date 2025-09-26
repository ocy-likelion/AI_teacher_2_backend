package com.ll.ilta.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class JoinDTO {

        private String nickname;
        private String email;
        private String role;
        @Schema(description = "프로필 이미지 URL", example = "https://image.com/profile.jpg")
        private String profileImageUrl;
    }

    @Getter
    public static class UpdateMemberDTO {

        private String nickname;
    }

    @Getter
    public static class LoginRequestDTO {

        private String email;
        @Schema(description = "인가 코드", example = "xxxxx")
        private String code;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChildDTO {

        @Schema(description = "자녀 이름", example = "홍길동")
        private String childName;

        @Schema(description = "자녀 학년", example = "3")
        private Integer childGrade;
    }
}

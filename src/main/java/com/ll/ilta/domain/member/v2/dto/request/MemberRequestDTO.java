package com.ll.ilta.domain.member.v2.dto.request;

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
        private String password;
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
        private String password;
        @Schema(description = "인가 코드", example = "xxxxx")
        private String code;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChildInfoDTO {

        @Schema(description = "자녀 이름", example = "홍길동")
        private String childName;

        @Schema(description = "자녀 학년", example = "3")
        private Integer childGrade;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateChildInfoDTO {

        @Schema(description = "자녀 이름", example = "홍길순")
        private String childName;

        @Schema(description = "자녀 학년", example = "4")
        private Integer childGrade;
    }
}

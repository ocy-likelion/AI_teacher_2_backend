package com.ll.ilta.domain.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class JoinResultDTO {

        private Long memberId;
        private String nickname;
        private String email;
        private String accessToken;
        private LocalDateTime createAt;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberPreviewDTO {

        private String nickname;
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberPreviewListDTO {

        List<MemberPreviewDTO> memberPreviewDTOList;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ChildResponseDTO {

        private String childName;
        private Integer childGrade;
    }
}

package com.ll.ilta.domain.member.v2.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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

        private Long memberId;
        private String nickname;
        private LocalDateTime updateAt;
        private LocalDateTime createAt;
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
    public static class ChildInfoDTO {

        @Schema(description = "자녀 ID", example = "2")
        private Long childId;

        @Schema(description = "자녀 이름", example = "홍길동")
        private String childName;

        @Schema(description = "자녀 학년", example = "3")
        private Integer childGrade;

        @Schema(description = "수정일", example = "2025-07-26T12:10:00")
        private LocalDateTime updatedAt;

        @Schema(description = "등록일", example = "2025-07-26T12:00:00")
        private LocalDateTime createdAt;
    }
}

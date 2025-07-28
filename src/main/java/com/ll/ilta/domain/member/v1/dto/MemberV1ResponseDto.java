package com.ll.ilta.domain.member.v1.dto;

import com.ll.ilta.domain.member.v1.entity.MemberV1;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberV1ResponseDto {

    private final Long id;
    private final String name;
    private final Integer grade;

    @Builder
    private MemberV1ResponseDto(Long id, String name, String username, Integer grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public static MemberV1ResponseDto from(MemberV1 memberV1) {
        return MemberV1ResponseDto.builder()
            .id(memberV1.getId())
            .name(memberV1.getName())
            .username(memberV1.getUsername())
            .grade(memberV1.getGrade())
            .build();
    }
}

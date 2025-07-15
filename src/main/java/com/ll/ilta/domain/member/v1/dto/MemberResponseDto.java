package com.ll.ilta.domain.member.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String name;
    private String username;
    private Integer grade;

    public static MemberResponseDto fromEntity(com.ll.ilta.domain.member.v1.entity.Member member) {
        return new MemberResponseDto(
            member.getId(),
            member.getUsername(),
            member.getName(),
            member.getGrade()
        );
    }
}

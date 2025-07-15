package com.ll.ilta.domain.member.v1.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

    private String name;
    private Integer grade;
    private String username;
    private String password;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberRequestDto(String username, String password, String name, Integer grade) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.grade = grade;
    }

    // 정적 팩토리 메서드
    public static MemberRequestDto of(String username, String password, String name, Integer grade) {
        return MemberRequestDto.builder()
            .username(username)
            .password(password)
            .name(name)
            .grade(grade)
            .build();
    }

}

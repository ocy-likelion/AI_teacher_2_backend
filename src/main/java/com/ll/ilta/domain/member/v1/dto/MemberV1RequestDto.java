package com.ll.ilta.domain.member.v1.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberV1RequestDto {

    private final Long id;
    private final String name;
    private final Integer grade;
    private final String username;
    private final String password;

    @Builder
    private MemberV1RequestDto(Long id, String username, String password, String name, Integer grade) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.grade = grade;
    }

    public static MemberV1RequestDto of(Long id, String username, String password, String name, Integer grade) {
        return MemberV1RequestDto.builder()
            .id(id)
            .username(username)
            .password(password)
            .name(name)
            .grade(grade)
            .build();
    }
}

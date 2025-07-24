//package com.ll.ilta.domain.member.v1.dto;
//
//import com.ll.ilta.domain.member.v1.entity.Member;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//public class MemberResponseDto {
//
//    private final Long id;
//    private final String name;
//    private final Integer grade;
//
//    @Builder
//    private MemberResponseDto(Long id, String name, String username, Integer grade) {
//        this.id = id;
//        this.name = name;
//        this.grade = grade;
//    }
//
//    public static MemberResponseDto from(Member member) {
//        return MemberResponseDto.builder()
//            .id(member.getId())
//            .name(member.getName())
//            .username(member.getUsername())
//            .grade(member.getGrade())
//            .build();
//    }
//}

package com.ll.ilta.domain.member.converter;

import com.ll.ilta.domain.member.dto.MemberRequestDTO;
import com.ll.ilta.domain.member.dto.MemberResponseDTO;
import com.ll.ilta.domain.member.dto.MemberResponseDTO.ChildDTO;
import com.ll.ilta.domain.member.entity.Member;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberConverter {

    public static Member toMember(MemberRequestDTO.JoinDTO joinDTO) {
        return Member.builder()
            .nickname(joinDTO.getNickname())
            .email(joinDTO.getEmail())
            .role(joinDTO.getRole())
            .build();
    }

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member, String accessToken) {
        return MemberResponseDTO.JoinResultDTO.builder()
            .memberId(member.getId())
            .createAt(member.getCreatedAt())
            .nickname(member.getNickname())
            .email(member.getEmail())
            .accessToken(accessToken)
            .build();
    }

    public static MemberResponseDTO.MemberPreviewDTO toMemberPreviewDTO(Member member) {
        MemberResponseDTO.MemberPreviewDTO dto = MemberResponseDTO.MemberPreviewDTO.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .updateAt(member.getUpdatedAt())
            .createAt(member.getCreatedAt())
            .build();
        return dto;
    }

    public static ChildDTO toChildDto(Member member) {
        ChildDTO childDTO = ChildDTO.builder()
            .memberId(member.getId())
            .childName(member.getChildName())
            .childGrade(member.getChildGrade())
            .build();
        return childDTO;
    }

    public static MemberResponseDTO.MemberPreviewListDTO toMemberPreviewListDTO(List<Member> memberList) {
        List<MemberResponseDTO.MemberPreviewDTO> memberPreviewDTOList = memberList.stream()
            .map(MemberConverter::toMemberPreviewDTO)
            .toList();

        return MemberResponseDTO.MemberPreviewListDTO.builder()
            .memberPreviewDTOList(memberPreviewDTOList)
            .build();
    }
}

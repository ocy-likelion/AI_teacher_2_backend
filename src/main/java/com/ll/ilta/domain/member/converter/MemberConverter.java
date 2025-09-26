package com.ll.ilta.domain.member.converter;

import com.ll.ilta.domain.member.dto.MemberRequestDTO.JoinDTO;
import com.ll.ilta.domain.member.dto.MemberResponseDTO.ChildResponseDTO;
import com.ll.ilta.domain.member.dto.MemberResponseDTO.JoinResultDTO;
import com.ll.ilta.domain.member.dto.MemberResponseDTO.MemberPreviewDTO;
import com.ll.ilta.domain.member.dto.MemberResponseDTO.MemberPreviewListDTO;
import com.ll.ilta.domain.member.entity.Member;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberConverter {

    public static Member toMember(JoinDTO joinDTO) {
        return Member.builder().nickname(joinDTO.getNickname()).email(joinDTO.getEmail()).role(joinDTO.getRole())
            .build();
    }

    public static JoinResultDTO toJoinResultDTO(Member member, String accessToken) {
        return JoinResultDTO.builder().memberId(member.getId()).createAt(member.getCreatedAt())
            .nickname(member.getNickname()).email(member.getEmail()).accessToken(accessToken).build();
    }

    public static MemberPreviewDTO toMemberPreviewDTO(Member member) {
        MemberPreviewDTO dto = MemberPreviewDTO.builder().memberId(member.getId()).nickname(member.getNickname())
            .updateAt(member.getUpdatedAt()).createAt(member.getCreatedAt()).build();
        return dto;
    }

    public static ChildResponseDTO toChildDto(Member member) {
        ChildResponseDTO childResponseDTO = ChildResponseDTO.builder().memberId(member.getId())
            .childName(member.getChildName()).childGrade(member.getChildGrade()).build();
        return childResponseDTO;
    }

    public static MemberPreviewListDTO toMemberPreviewListDTO(List<Member> memberList) {
        List<MemberPreviewDTO> memberPreviewDTOList = memberList.stream().map(MemberConverter::toMemberPreviewDTO)
            .toList();

        return MemberPreviewListDTO.builder().memberPreviewDTOList(memberPreviewDTOList).build();
    }
}

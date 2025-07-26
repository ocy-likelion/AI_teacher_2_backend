package com.ll.ilta.domain.member.v2.converter;

import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.dto.response.MemberResponseDTO;
import com.ll.ilta.domain.member.v2.entity.Member;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {

    public static Member toMember(MemberRequestDTO.JoinDTO joinDTO, PasswordEncoder passwordEncoder) {
        return Member.builder()
            .nickname(joinDTO.getNickname())
            .password(passwordEncoder.encode(joinDTO.getPassword()))
            .email(joinDTO.getEmail())
            .role(joinDTO.getRole())
            .build();
    }

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
            .memberId(member.getId())
            .createAt(member.getCreatedAt())
            .build();
    }

    public static MemberResponseDTO.MemberPreviewDTO toMemberPreviewDTO(Member member) {
        return MemberResponseDTO.MemberPreviewDTO.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .updateAt(member.getUpdatedAt())
            .createAt(member.getCreatedAt())
            .build();
    }

    public static MemberResponseDTO.MemberPreviewListDTO toMemberPreviewListDTO(List<Member> memberList) {
        List<MemberResponseDTO.MemberPreviewDTO> memberPreviewDTOList = memberList.stream()
            .map(MemberConverter::toMemberPreviewDTO)
            .toList();

        return MemberResponseDTO.MemberPreviewListDTO.builder()
            .memberPreviewDTOList(memberPreviewDTOList)
            .build();
    }

//    public static MemberResponseDTO.ChildInfoDTO toChildInfoDTO(Member member) {
//        return MemberResponseDTO.ChildInfoDTO.builder()
//            .childId(member.getId())
//            .childName(member.getName())
//            .childGrade(member.getGrade())
//            .createdAt(member.getCreatedAt())
//            .updatedAt(member.getUpdatedAt())
//            .build();
//    }
}

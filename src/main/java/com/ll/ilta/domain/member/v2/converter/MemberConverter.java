package com.ll.ilta.domain.member.v2.converter;

import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.dto.response.MemberResponseDTO;
import com.ll.ilta.domain.member.v2.entity.Member;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class MemberConverter {

    public static Member toMember(MemberRequestDTO.JoinDTO joinDTO, PasswordEncoder passwordEncoder) {
        return Member.builder()
            .nickname(joinDTO.getNickname())
            .password(passwordEncoder.encode(joinDTO.getPassword()))
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
            // return MemberResponseDTO.MemberPreviewDTO.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .updateAt(member.getUpdatedAt())
            .createAt(member.getCreatedAt())
            .build();
        log.info("DTO 변환 결과 => memberId: {}, nickname: {}, createdAt: {}, updatedAt: {}",
            dto.getMemberId(), dto.getNickname(), dto.getCreateAt(), dto.getUpdateAt());
        return dto;
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

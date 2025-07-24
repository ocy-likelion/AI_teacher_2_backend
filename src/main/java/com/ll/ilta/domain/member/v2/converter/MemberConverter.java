package com.ll.ilta.domain.member.v2.converter;

import com.ll.ilta.domain.member.v2.dto.Member;
import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.dto.response.MemberResponseDTO;
import java.util.List;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {

    public static Member tomember(MemberRequestDTO.JoinDTO joinDTO, PasswordEncoder passwordEncoder) {
        return Member.builder()
            .name(joinDTO.getName())
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
            .name(member.getName())
            .updateAt(member.getUpdatedAt())
            .createAt(member.getCreatedAt())
            .build();
    }

    public static MemberResponseDTO.MemberPreviewListDTO toUserPreviewListDTO(List<User> userList) {
        List<MemberResponseDTO.MemberPreviewDTO> userPreviewDTOList = userList.stream()
            .map(MemberConverter::toMemberPreviewDTO)
            .toList();

        return MemberResponseDTO.MemberPreviewListDTO.builder()
            .memberPreviewDTOList(userPreviewDTOList)
            .build();
    }
}

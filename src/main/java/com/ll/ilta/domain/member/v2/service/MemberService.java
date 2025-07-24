package com.ll.ilta.domain.member.v2.service;

import com.ll.ilta.domain.member.v2.dto.Member;
import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import java.util.List;

public interface MemberService {

    Member createMember(MemberRequestDTO.JoinDTO joinDTO);

    Member readMember(Long memberId);

    List<Member> readMembers();

    void deleteMember(Long memberId);

    Member updateMember(MemberRequestDTO.UpdateMemberDTO updateMemberDTO, Long memberId);
}

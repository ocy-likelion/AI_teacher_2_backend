package com.ll.ilta.domain.member.v2.service;

import com.ll.ilta.domain.member.v2.dto.request.MemberRequestDTO;
import com.ll.ilta.domain.member.v2.entity.Member;
import java.util.List;

public interface MemberService {

    Member createMember(MemberRequestDTO.JoinDTO joinDTO);

    Member readMember(Long memberId); // 로그인 사용자 기준

    List<Member> readAllMember();

    boolean checkChildInfo(Long memberId); // 로그인 사용자 기준

    void deleteMyInfo(Long memberId); // 로그인 사용자 기준

    Member updateMyInfo(MemberRequestDTO.UpdateMemberDTO updateMemberDTO, Long memberId); // 로그인 사용자 기준
}

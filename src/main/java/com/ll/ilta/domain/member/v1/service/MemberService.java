package com.ll.ilta.domain.member.v1.service;

import com.ll.ilta.domain.member.v1.dto.MemberLoginRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberResponseDto;

public interface MemberService {

    void login(MemberLoginRequestDto request);

    void createChild(MemberRequestDto request);

    MemberResponseDto updateChild(Long memberId, MemberRequestDto request);

    MemberResponseDto getChild(Long memberId);
}

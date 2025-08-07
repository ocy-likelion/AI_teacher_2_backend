package com.ll.ilta.domain.member.v1.service;

import com.ll.ilta.domain.member.v1.dto.MemberV1LoginRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberV1RequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberV1ResponseDto;
import com.ll.ilta.domain.member.v1.entity.MemberV1;

public interface MemberV1Service {

    String login(MemberV1LoginRequestDto request);

    void createChild(MemberV1RequestDto request);

    MemberV1ResponseDto updateChild(Long memberId, MemberV1RequestDto request);

    MemberV1ResponseDto getChild(Long memberId);

    MemberV1 findById(Long memberId);
}

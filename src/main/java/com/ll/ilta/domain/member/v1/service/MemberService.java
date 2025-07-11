package com.ll.ilta.domain.member.v1.service;

import com.ll.ilta.domain.member.v1.dto.MemberLoginRequestDto;
import com.ll.ilta.domain.member.v1.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean login(MemberLoginRequestDto dto) {
        return memberRepository.findByUsername(dto.getUsername())
            .map(member -> member.getPassword().equals(dto.getPassword()))
            .orElse(false);
    }

}

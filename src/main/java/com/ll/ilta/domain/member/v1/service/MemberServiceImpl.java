package com.ll.ilta.domain.member.v1.service;

import com.ll.ilta.domain.member.v1.dto.MemberLoginRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberResponseDto;
import com.ll.ilta.domain.member.v1.entity.Member;
import com.ll.ilta.domain.member.v1.repository.MemberRepository;
import com.ll.ilta.global.exception.CustomException;
import com.ll.ilta.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void login(MemberLoginRequestDto request) {
        Member member = memberRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    @Override
    public void createChild(MemberRequestDto request) {
        Member member = Member.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .grade(request.getGrade())
            .build();
        memberRepository.save(member);
    }

    @Override
    public MemberResponseDto updateChild(Long memberId, MemberRequestDto request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        member.update(request.getName(), request.getGrade());
        memberRepository.save(member);
        return MemberResponseDto.from(member);
    }

    @Override
    public MemberResponseDto getChild(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        return MemberResponseDto.from(member);
    }
}

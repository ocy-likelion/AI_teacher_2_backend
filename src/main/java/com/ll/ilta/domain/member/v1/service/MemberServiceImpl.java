package com.ll.ilta.domain.member.v1.service;

import com.ll.ilta.domain.member.v1.dto.MemberLoginRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberResponseDto;
import com.ll.ilta.domain.member.v1.entity.Member;
import com.ll.ilta.domain.member.v1.repository.MemberRepository;
import com.ll.ilta.global.exception.CustomException;
import com.ll.ilta.global.exception.ErrorCode;
import com.ll.ilta.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(MemberLoginRequestDto request) {
        log.info("Login attempt: username={}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.createToken(request.getUsername());
        log.info("JWT token generated: {}", token);
        return token;
    }

    @Override
    public void createChild(MemberRequestDto request) {
        Member member = Member.builder().username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword())).name(request.getName()).grade(request.getGrade())
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

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }
}

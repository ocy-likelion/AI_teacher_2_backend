package com.ll.ilta.domain.member.v1.service;

import com.ll.ilta.domain.member.v1.dto.MemberV1LoginRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberV1RequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberV1ResponseDto;
import com.ll.ilta.domain.member.v1.entity.MemberV1;
import com.ll.ilta.domain.member.v1.repository.MemberV1Repository;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
import com.ll.ilta.global.security.v1.jwt.JwtTokenProvider;
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
public class MemberV1ServiceImpl implements MemberV1Service {

    private final MemberV1Repository memberV1Repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(MemberV1LoginRequestDto request) {
        log.info("Login attempt: username={}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.createToken(request.getUsername());
        log.info("JWT token generated: {}", token);
        return token;
    }

    @Override
    public void createChild(MemberV1RequestDto request) {
        MemberV1 memberV1 = MemberV1.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .grade(request.getGrade())
            .build();
        memberV1Repository.save(memberV1);
    }

    @Override
    public MemberV1ResponseDto updateChild(Long memberId, MemberV1RequestDto request) {
        MemberV1 memberV1 = memberV1Repository.findById(memberId)
            .orElseThrow(() -> new AuthHandler(ErrorStatus.NOT_FOUND_USER));
        memberV1.update(request.getName(), request.getGrade());
        memberV1Repository.save(memberV1);
        return MemberV1ResponseDto.from(memberV1);
    }

    @Override
    public MemberV1ResponseDto getChild(Long memberId) {
        MemberV1 memberV1 = memberV1Repository.findById(memberId)
            .orElseThrow(() -> new AuthHandler(ErrorStatus.NOT_FOUND_USER));
        return MemberV1ResponseDto.from(memberV1);
    }

    @Override
    public MemberV1 findById(Long memberId) {
        return memberV1Repository.findById(memberId).orElseThrow(() -> new AuthHandler(ErrorStatus.NOT_FOUND_USER));
    }
}

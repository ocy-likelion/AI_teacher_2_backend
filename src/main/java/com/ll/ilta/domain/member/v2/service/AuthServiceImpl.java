package com.ll.ilta.domain.member.v2.service;

import com.ll.ilta.domain.member.v2.converter.AuthConverter;
import com.ll.ilta.domain.member.v2.dto.KakaoDTO;
import com.ll.ilta.domain.member.v2.dto.Member;
import com.ll.ilta.domain.member.v2.repository.MemberRepository;
import com.ll.ilta.global.util.JwtUtil;
import com.ll.ilta.global.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestMemberProfile(oAuthToken);

        Optional<Member> queryMember = memberRepository.findByEmail(kakaoProfile.getKakao_account().getEmail());

        if (queryMember.isPresent()) {
            Member member = queryMember.get();
            httpServletResponse.setHeader("Authorization", jwtUtil.createAccessToken(member.getEmail(), "ROLE_USER"));
            return member;
        } else {
            Member member = AuthConverter.toMember(kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getProfile().getNickname(),
                "1234",
                passwordEncoder);
            memberRepository.save(member);
            httpServletResponse.setHeader("Authorization",
                jwtUtil.createAccessToken(member.getEmail(), String.valueOf(member.getRole())));
            return member;
        }
    }
}

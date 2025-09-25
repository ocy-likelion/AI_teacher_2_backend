package com.ll.ilta.domain.member.service;

import com.ll.ilta.domain.member.converter.AuthConverter;
import com.ll.ilta.domain.member.converter.MemberConverter;
import com.ll.ilta.domain.member.dto.KakaoDTO;
import com.ll.ilta.domain.member.dto.response.MemberResponseDTO;
import com.ll.ilta.domain.member.entity.Member;
import com.ll.ilta.domain.member.repository.MemberRepository;
import com.ll.ilta.global.security.jwt.JwtUtil;
import com.ll.ilta.global.security.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    public MemberResponseDTO.JoinResultDTO  oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        log.info("oAuthLogin called with code={}", accessCode);
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestMemberProfile(oAuthToken);

        Optional<Member> queryMember = memberRepository.findByEmail(kakaoProfile.getKakao_account().getEmail());

        if (queryMember.isPresent()) {
            Member member = queryMember.get();
            String token = jwtUtil.createAccessToken(member.getEmail(), "ROLE_USER");
            httpServletResponse.setHeader("Authorization", "Bearer " + token);
            return MemberConverter.toJoinResultDTO(member, token);
        } else {
            Member member = AuthConverter.toMember(kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getProfile().getNickname());
            memberRepository.save(member);

            String token = jwtUtil.createAccessToken(member.getEmail(), member.getRole());
            httpServletResponse.setHeader("Authorization", "Bearer " + token);
            return MemberConverter.toJoinResultDTO(member, token);
        }
    }
}

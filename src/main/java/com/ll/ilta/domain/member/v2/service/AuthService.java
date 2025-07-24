package com.ll.ilta.domain.member.v2.service;


import com.ll.ilta.domain.member.v2.dto.KakaoDTO;
import com.ll.ilta.domain.member.v2.dto.Member;
import com.ll.ilta.domain.member.v2.repository.MemberRepository;
import com.ll.ilta.global.util.JwtUtil;
import com.ll.ilta.global.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoUtil kakaoUtil;
    private final MemberRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);

    }
}

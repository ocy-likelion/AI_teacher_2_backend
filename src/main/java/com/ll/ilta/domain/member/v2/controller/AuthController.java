package com.ll.ilta.domain.member.v2.controller;

import com.ll.ilta.domain.member.v2.converter.MemberConverter;
import com.ll.ilta.domain.member.v2.dto.Member;
import com.ll.ilta.domain.member.v2.service.AuthService;
import com.ll.ilta.global.payload.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//, produces = APPLICATION_JSON_VALUE
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/api/v1/oauth") // Redirect URI
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode,
        HttpServletResponse httpServletResponse) {
        Member member = authService.oAuthLogin(accessCode, httpServletResponse);
        return BaseResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }
}

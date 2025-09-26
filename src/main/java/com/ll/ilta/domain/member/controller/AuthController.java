package com.ll.ilta.domain.member.controller;

import com.ll.ilta.domain.member.dto.MemberResponseDTO.JoinResultDTO;
import com.ll.ilta.domain.member.service.AuthService;
import com.ll.ilta.global.payload.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "oauth", description = "카카오 OAuth API")
@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 로그인", description = "카카오 인가코드를 이용해 로그인 및 자동 회원가입 처리")
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = JoinResultDTO.class)))
    @GetMapping("/oauth") // Redirect URI
    public BaseResponse<JoinResultDTO> kakaoLogin(@RequestParam("code") String accessCode,
        HttpServletResponse httpServletResponse) {
        JoinResultDTO result = authService.oAuthLogin(accessCode, httpServletResponse);
        return BaseResponse.onSuccess(result);
    }

}

package com.ll.ilta.domain.member.v1.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ll.ilta.domain.member.v1.dto.MemberLoginRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberResponseDto;
import com.ll.ilta.domain.member.v1.service.MemberService;
import com.ll.ilta.global.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MemberV1Controller", description = "회원 및 자녀 API")
@RestController
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberV1Controller {

    private final MemberService memberService;

    @Operation(summary = "로그인 ", description = "아이디/비밀번호 기반 로그인")
    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody @Valid MemberLoginRequestDto loginRequest) {
        memberService.login(loginRequest);
        return ResponseEntity.ok(MessageResponse.of("✅ 로그인 성공"));
    }

    @Operation(summary = "자녀 생성", description = "자녀 정보를 등록")
    @PostMapping
    public ResponseEntity<MessageResponse> createChild(@RequestBody @Valid MemberRequestDto request) {
        memberService.createChild(request);
        return ResponseEntity.ok(MessageResponse.of("자녀 등록 완료"));
    }

    @Operation(summary = "자녀 수정", description = "자녀 이름 및 학년 수정")
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateChild(@PathVariable Long memberId,
        @RequestBody @Valid MemberRequestDto request) {
        return ResponseEntity.ok(memberService.updateChild(memberId, request));
    }

    @Operation(
        summary = "자녀 조회",
        description = "자녀 ID 기준 단건 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "자녀 없음",
                content = @Content(schema = @Schema(implementation = com.ll.ilta.global.response.ErrorResponse.class)))
        }
    )
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getChild(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getChild(memberId));
    }
}

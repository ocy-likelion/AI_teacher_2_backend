package com.ll.ilta.domain.member.v1.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ll.ilta.domain.member.v1.dto.MemberV1LoginRequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberV1LoginResponseDto;
import com.ll.ilta.domain.member.v1.dto.MemberV1RequestDto;
import com.ll.ilta.domain.member.v1.dto.MemberV1ResponseDto;
import com.ll.ilta.domain.member.v1.service.MemberV1Service;
import com.ll.ilta.global.payload.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //TODO 로그인 기능 개발 완료 후 제거 예정
@Tag(name = "MemberV1Controller", description = "회원 및 자녀 API")
@RestController
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberV1Controller {

    private final MemberV1Service memberv1Service;

    @Operation(summary = "로그인 ", description = "아이디/비밀번호 기반 로그인")
    @PostMapping("/login")
    public ResponseEntity<MemberV1LoginResponseDto> login(@RequestBody @Valid MemberV1LoginRequestDto loginRequest) {
        log.info("Login API called for username={}", loginRequest.getUsername());
        String token = memberv1Service.login(loginRequest);
        log.info("Login API generated token: {}", token);

        MemberV1LoginResponseDto responseDto = new MemberV1LoginResponseDto("✅ 로그인 성공", token);

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "자녀 생성", description = "자녀 정보를 등록")
    @PostMapping
    public ResponseEntity<?> createChild(@RequestBody @Valid MemberV1RequestDto request) {
        memberv1Service.createChild(request);
        return ResponseEntity.ok(BaseResponse.onSuccess("자녀 등록 완료"));
    }

    @Operation(summary = "자녀 수정", description = "자녀 이름 및 학년 수정")
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberV1ResponseDto> updateChild(@PathVariable Long memberId,
        @RequestBody @Valid MemberV1RequestDto request) {
        return ResponseEntity.ok(memberv1Service.updateChild(memberId, request));
    }

    @Operation(
        summary = "자녀 조회",
        description = "자녀 ID 기준 단건 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "자녀 없음",
                content = @Content(schema = @Schema(implementation = com.ll.ilta.global.payload.BaseResponse.class)))
        }
    )
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberV1ResponseDto> getChild(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberv1Service.getChild(memberId));
    }
}

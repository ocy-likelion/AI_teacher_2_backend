package com.ll.ilta.domain.member.v1.controller;

import com.ll.ilta.domain.member.v1.dto.MemberLoginRequestDto;
import com.ll.ilta.domain.member.v1.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE)
@Tag(name = "MemberV1Controller", description = " 로그인  API")

public class MemberV1Controller {

    private final MemberService memberService;

    public MemberV1Controller(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 ")

    public ResponseEntity<String> login(@RequestBody MemberLoginRequestDto dto) {
        boolean isSuccess = memberService.login(dto);

        if (isSuccess) {
            return ResponseEntity.ok("✅ 로그인 성공");
        } else {
            return ResponseEntity.status(401).body("로그인 실패");
        }
    }
}

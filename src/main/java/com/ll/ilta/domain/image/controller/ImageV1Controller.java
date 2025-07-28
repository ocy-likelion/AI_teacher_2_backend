package com.ll.ilta.domain.image.controller;

import com.ll.ilta.domain.problem.dto.ProblemResponseDto;
import com.ll.ilta.domain.problem.service.ProblemService;
import com.ll.ilta.global.security.v2.member.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ImageV1Controller", description = "이미지 업로드 및 문제/해설 생성 API")
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
@RestController
public class ImageV1Controller {

    private final ProblemService problemService;

    @Operation(summary = "업로드 및 저장", description = "이미지 업로드 및 문제/해설 생성")
    @PostMapping("/upload")
    public ResponseEntity<ProblemResponseDto> uploadImage(@RequestParam("file") MultipartFile file,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId(); // TODO: 로그인한 사용자 ID변경
        ProblemResponseDto response = problemService.createProblemWithImage(memberId, file);
        return ResponseEntity.ok(response);
    }
}

package com.ll.ilta.domain.image.controller;

import com.ll.ilta.domain.problem.dto.ProblemResponseDto;
import com.ll.ilta.domain.problem.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProblemResponseDto> uploadImage(@RequestParam("file") MultipartFile file) {
        Long userId = 8L; // TODO: 인증 구현 후 실제 로그인한 유저 ID로 변경
        ProblemResponseDto response = problemService.createProblemWithImage(userId, file);
        return ResponseEntity.ok(response);
    }
}

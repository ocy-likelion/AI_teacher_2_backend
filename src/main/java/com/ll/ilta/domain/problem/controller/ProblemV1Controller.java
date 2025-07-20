package com.ll.ilta.domain.problem.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ll.ilta.domain.problem.dto.ProblemResponseDto;
import com.ll.ilta.domain.problem.service.ProblemService;
import com.ll.ilta.global.common.dto.CursorPaginatedResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ProblemV1Controller", description = "문제와 관련된 CRUD 작업을 수행하는 API")
@RequestMapping(value = "/api/v1/problem", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class ProblemV1Controller {

    private final ProblemService problemService;

    @Operation(summary = "해설 목록 조회", description = "커서 기반 페이징 지원")
    @GetMapping("/list")
    public ResponseEntity<CursorPaginatedResponseDto<ProblemResponseDto>> getProblemList(@RequestParam Long memberId,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(name = "after_cursor", required = false) String afterCursor) {
        CursorPaginatedResponseDto<ProblemResponseDto> response = problemService.getProblemList(memberId, limit,
            afterCursor);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해설 단건 조회", description = "문제의 상세 정보 조회")
    @GetMapping
    public ResponseEntity<ProblemResponseDto> getProblem(@RequestParam Long problemId) {
        ProblemResponseDto response = problemService.getProblemDetail(problemId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해설 삭제", description = "문제 삭제")
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteProblem(@RequestParam Long problemId) {
        problemService.deleteProblem(problemId);
        return ResponseEntity.ok(Map.of("message", "문제가 삭제되었습니다."));
    }
}

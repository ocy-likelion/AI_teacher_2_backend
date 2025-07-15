package com.ll.ilta.domain.problem.controller;

import com.ll.ilta.domain.problem.dto.ProblemDto;
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

@Tag(name = "ProblemV1Controller", description = "문제와 관련된 CRUD 작업을 수행하는 API입니다.")
@RequestMapping("/api/v1/problem")
@RequiredArgsConstructor
@RestController
public class ProblemV1Controller {

    private final ProblemService problemService;

    @Operation(summary = "해설 목록 조회", description = "자녀의 문제 목록을 커서 기반으로 조회합니다. `after_cursor` 파라미터를 사용하여 페이지네이션을 지원합니다.")
    @GetMapping("/list")
    public ResponseEntity<CursorPaginatedResponseDto<ProblemDto>> getProblemList(
        @RequestParam Long memberId,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(name = "after_cursor", required = false) String afterCursor
    ) {
        CursorPaginatedResponseDto<ProblemDto> response = problemService.getProblemList(memberId, limit, afterCursor);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해설 단건 조회", description = "문제 ID를 사용하여 문제의 상세 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<ProblemDto> getProblem(@RequestParam Long problemId) {
        ProblemDto response = problemService.getProblemDetail(problemId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해설 삭제", description = "문제 ID를 사용하여 문제를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteProblem(@RequestParam Long problemId) {
        problemService.deleteProblem(problemId);
        return ResponseEntity.ok(Map.of("message", "문제가 삭제되었습니다."));
    }
}

package com.ll.ilta.domain.problem.repository;

import com.ll.ilta.domain.problem.dto.ProblemResponseDto;
import java.util.List;

public interface ProblemRepositoryCustom {

    List<ProblemResponseDto> findProblemWithCursor(Long memberId, String afterCursor, int limit);

    ProblemResponseDto findProblemById(Long problemId);
}

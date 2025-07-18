package com.ll.ilta.domain.problem.repository;

import com.ll.ilta.domain.problem.dto.ProblemDto;
import java.util.List;

public interface ProblemRepositoryCustom {

    List<ProblemDto> findProblemWithCursor(Long memberId, String afterCursor, int limit);

    ProblemDto findProblemById(Long problemId);
}

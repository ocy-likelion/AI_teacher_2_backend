package com.ll.ilta.domain.problem.service;

import com.ll.ilta.domain.problem.dto.ProblemDto;

import com.ll.ilta.domain.problem.repository.FavoriteRepository;
import com.ll.ilta.domain.problem.repository.ProblemRepository;
import com.ll.ilta.global.common.dto.CursorPaginatedResponse;
import com.ll.ilta.global.common.service.CursorUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final FavoriteRepository favoriteRepository;
    private static final String PROBLEMS_LIST_URL = "/api/v1/problems/list";

    public CursorPaginatedResponse<ProblemDto> getProblemList (Long childId, int limit, String afterCursor) {
        List<ProblemDto> problems = problemRepository.findProblemWithCursor(childId, afterCursor, limit+1);

        if (problems.isEmpty()) {
            return CursorPaginatedResponse.of(problems, limit, false, null,
                buildSelfUrl(limit, afterCursor), null);
        }

        boolean hasNextPage = problems.size() > limit;
        if (hasNextPage) {
            problems = problems.subList(0, limit);
        }

        String nextCursor = null;
        if (hasNextPage) {
            ProblemDto lastProblem = problems.get(problems.size() - 1);
            nextCursor = CursorUtil.encodeCursor(lastProblem.getId(), lastProblem.getCreatedAt());
        }

        String selfUrl = buildSelfUrl(limit, afterCursor);
        String nextUrl = hasNextPage ? buildNextUrl(limit, nextCursor) : null;

        return CursorPaginatedResponse.of(problems, limit, hasNextPage, nextCursor, selfUrl, nextUrl);

    }

    private String buildSelfUrl(int limit, String afterCursor) {
        StringBuilder url = new StringBuilder(PROBLEMS_LIST_URL + "?limit=").append(limit);
        if (afterCursor != null) {
            url.append("&after_cursor=").append(afterCursor);
        }
        return url.toString();
    }

    private String buildNextUrl(int limit, String nextCursor) {
        return PROBLEMS_LIST_URL + "?limit=" + limit + "&after_cursor=" + nextCursor;
    }

    public ProblemDto getProblemDetail(Long problemId) {
        return problemRepository.findProblemById(problemId);
    }

    @Transactional
    public void deleteProblem(Long problemId) {
        favoriteRepository.deleteByProblemId(problemId);
        problemRepository.deleteById(problemId);
    }
}


package com.ll.ilta.domain.problem.repository;

import static com.ll.ilta.domain.favorite.entity.QFavorite.favorite;
import static com.ll.ilta.domain.problem.entity.QProblem.problem;
import static com.ll.ilta.domain.problem.entity.QProblemImage.problemImage;
import static com.ll.ilta.domain.problem.entity.QProblemResult.problemResult;

import com.ll.ilta.domain.problem.dto.ProblemDto;
import com.ll.ilta.global.common.service.CursorUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemRepositoryImpl implements ProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProblemDto> findProblemWithCursor(Long childId, String afterCursor, int limit) {
        BooleanBuilder builder = new BooleanBuilder(problem.child.id.eq(childId));
        if (afterCursor != null) {
            CursorUtil.Cursor decoded = CursorUtil.decodeCursor(afterCursor);
            builder.and(
                problem.createdAt.lt(decoded.createdAt())
                    .or(
                        problem.createdAt.eq(decoded.createdAt())
                            .and(problem.id.lt(decoded.id()))
                    )
            );
        }

        List<Long> problemIds = queryFactory
            .select(problem.id)
            .from(problem)
            .where(builder)
            .orderBy(problem.createdAt.desc(), problem.id.desc())
            .limit(limit + 1)
            .fetch();

        boolean hasNext = problemIds.size() > limit;
        if (hasNext) {
            problemIds = problemIds.subList(0, limit);
        }

        if (problemIds.isEmpty()) {
            return List.of();
        }

        return fetchProblems(childId, problemIds);
    }

    @Override
    public ProblemDto findProblemById(Long problemId) {
        List<ProblemDto> problems = queryFactory
            .select(Projections.constructor(ProblemDto.class,
                problem.id,
                problem.image.id,
                favorite.id.isNotNull(),
                problem.result.ocrResult,
                problem.result.llmResult,
                problem.createdAt
            ))
            .from(problem)
            .leftJoin(problem.image, problemImage)
            .leftJoin(problem.result, problemResult)
            .leftJoin(favorite).on(favorite.problem.id.eq(problem.id))
            .where(problem.id.eq(problemId))
            .fetch();

        if (problems.isEmpty()) {
            return null;
        }

        return problems.get(0);
    }

    private List<ProblemDto> fetchProblems(Long childId, List<Long> problemIds) {
        return queryFactory
            .select(Projections.constructor(ProblemDto.class,
                problem.id,
                problem.image.id,
                favorite.id.isNotNull(),
                problem.result.ocrResult,
                problem.result.llmResult,
                problem.createdAt
            ))
            .from(problem)
            .leftJoin(problem.image, problemImage)
            .leftJoin(problem.result, problemResult)
            .leftJoin(favorite).on(favorite.problem.id.eq(problem.id).and(favorite.child.id.eq(childId)))
            .where(problem.id.in(problemIds))
            .orderBy(problem.createdAt.desc(), problem.id.desc())
            .fetch();
    }
}

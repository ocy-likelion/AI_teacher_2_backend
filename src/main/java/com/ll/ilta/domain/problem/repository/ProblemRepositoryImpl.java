package com.ll.ilta.domain.problem.repository;

import static com.ll.ilta.domain.favorite.entity.QFavorite.favorite;
import static com.ll.ilta.domain.problem.entity.QConcept.concept;
import static com.ll.ilta.domain.problem.entity.QProblem.problem;
import static com.ll.ilta.domain.problem.entity.QProblemConcept.problemConcept;
import static com.ll.ilta.domain.problem.entity.QProblemImage.problemImage;
import static com.ll.ilta.domain.problem.entity.QProblemResult.problemResult;

import com.ll.ilta.domain.problem.dto.ProblemConceptDto;
import com.ll.ilta.domain.problem.dto.ProblemDto;
import com.ll.ilta.global.common.dto.Cursor;
import com.ll.ilta.global.common.service.CursorUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProblemRepositoryImpl implements ProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProblemDto> findProblemWithCursor(Long memberId, String afterCursor, int limit) {
        BooleanBuilder builder = new BooleanBuilder(problem.member.id.eq(memberId));
        if (afterCursor != null) {
            Cursor decoded = CursorUtil.decodeCursor(afterCursor);
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

        return buildProblemDtos(memberId, problemIds);
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

        Map<Long, List<ProblemConceptDto>> conceptMap = fetchConceptsMap(List.of(problemId));

        ProblemDto p = problems.getFirst();
        return ProblemDto.of(
            p.getId(),
            p.getImageId(),
            conceptMap.getOrDefault(p.getId(), List.of()),
            p.getFavorite(),
            p.getOcrResult(),
            p.getLlmResult(),
            p.getCreatedAt()
        );
    }

    private List<ProblemDto> buildProblemDtos(Long childId, List<Long> problemIds) {
        List<ProblemDto> problems = fetchProblems(childId, problemIds);
        Map<Long, List<ProblemConceptDto>> conceptMap = fetchConceptsMap(problemIds);

        return problems.stream()
            .map(p -> ProblemDto.of(
                p.getId(),
                p.getImageId(),
                conceptMap.getOrDefault(p.getId(), List.of()),
                p.getFavorite(),
                p.getOcrResult(),
                p.getLlmResult(),
                p.getCreatedAt()
            ))
            .toList();
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
            .leftJoin(favorite).on(favorite.problem.id.eq(problem.id).and(favorite.member.id.eq(childId)))
            .where(problem.id.in(problemIds))
            .orderBy(problem.createdAt.desc(), problem.id.desc())
            .fetch();
    }

    private Map<Long, List<ProblemConceptDto>> fetchConceptsMap(List<Long> problemIds) {
        List<ProblemConceptDto> concepts = queryFactory
            .select(Projections.constructor(ProblemConceptDto.class,
                problemConcept.id,
                problemConcept.concept.name,
                problemConcept.problem.id
            ))
            .from(problemConcept)
            .join(problemConcept.concept, concept)
            .where(problemConcept.problem.id.in(problemIds))
            .fetch();

        return concepts.stream()
            .collect(Collectors.groupingBy(ProblemConceptDto::getProblemId));
    }
}

package com.ll.ilta.domain.problem.repository;

import static com.ll.ilta.domain.favorite.entity.QFavorite.favorite;
import static com.ll.ilta.domain.concept.entity.QConcept.concept;
import static com.ll.ilta.domain.problem.entity.QProblem.problem;
import static com.ll.ilta.domain.problem.entity.QProblemConcept.problemConcept;
import static com.ll.ilta.domain.problem.entity.QProblemResult.problemResult;
import static com.ll.ilta.domain.image.entity.QImage.image;

import com.ll.ilta.domain.concept.dto.ConceptDto;
import com.ll.ilta.domain.problem.dto.ProblemResponseDto;
import com.ll.ilta.domain.problem.entity.ProblemResult;
import com.ll.ilta.global.common.dto.Cursor;
import com.ll.ilta.global.common.service.CursorUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
    public List<ProblemResponseDto> findProblemWithCursor(Long memberV1Id, String afterCursor, int limit) {
        BooleanBuilder builder = new BooleanBuilder(problem.memberV1.id.eq(memberV1Id));
        builder.and(problem.activatedAt.isNotNull());
        if (afterCursor != null) {
            Cursor decoded = CursorUtil.decodeCursor(afterCursor);
            builder.and(problem.activatedAt.isNotNull().and(problem.activatedAt.lt(decoded.activatedAt())
                .or(problem.activatedAt.eq(decoded.activatedAt()).and(problem.id.lt(decoded.id())))));
        }

        List<Long> problemIds = queryFactory.select(problem.id).from(problem).where(builder)
            .orderBy(problem.activatedAt.desc(), problem.id.desc()).limit(limit + 1).fetch();

        boolean hasNext = problemIds.size() > limit;
        if (hasNext) {
            problemIds = problemIds.subList(0, limit);
        }

        if (problemIds.isEmpty()) {
            return List.of();
        }

        return buildProblemDtos(memberV1Id, problemIds);
    }

    @Override
    public ProblemResponseDto findProblemById(Long problemId) {
        List<Long> problemIds = List.of(problemId);
        List<ProblemResponseDto> problems = buildProblemDtos(null, problemIds);

        return problems.isEmpty() ? null : problems.get(0);
    }

    private List<ProblemResponseDto> buildProblemDtos(Long memberV1Id, List<Long> problemIds) {
        List<ProblemResponseDto> problems = fetchProblems(memberV1Id, problemIds);

        Map<Long, String> imageUrlMap = fetchImageUrls(problemIds);

        Map<Long, ProblemResult> resultMap = fetchResultMap(problemIds);

        Map<Long, List<ConceptDto>> conceptMap = fetchConceptsMap(problemIds);

        return problems.stream().map(p -> {
            ProblemResult result = resultMap.get(p.getId());
            return ProblemResponseDto.of(p.getId(), imageUrlMap.getOrDefault(p.getId(), null),
                conceptMap.getOrDefault(p.getId(), List.of()), p.getFavorite(),
                result != null ? result.getOcrResult() : null, result != null ? result.getSummary() : null,
                result != null ? result.getExplanation() : null, p.getActivatedAt());
        }).toList();
    }

    private List<ProblemResponseDto> fetchProblems(Long memberV1Id, List<Long> problemIds) {
        BooleanBuilder builder = new BooleanBuilder(problem.id.in(problemIds));
        if (memberV1Id != null) {
            builder.and(problem.memberV1.id.eq(memberV1Id));
        }

        return queryFactory.select(
                Projections.constructor(ProblemResponseDto.class, problem.id, Expressions.nullExpression(String.class),
                    favorite.id.isNotNull(), Expressions.constant(""), Expressions.constant(""), Expressions.constant(""),
                    problem.activatedAt)).from(problem).leftJoin(favorite)
            .on(favorite.problem.id.eq(problem.id).and(memberV1Id != null ? favorite.memberV1.id.eq(memberV1Id) : null))
            .where(builder).orderBy(problem.activatedAt.desc(), problem.id.desc()).fetch();
    }

    private Map<Long, String> fetchImageUrls(List<Long> problemIds) {
        List<Tuple> tuples = queryFactory.select(image.problem.id, image.imageUrl).from(image)
            .where(image.problem.id.in(problemIds)).fetch();

        return tuples.stream().collect(Collectors.toMap(t -> t.get(image.problem.id), t -> t.get(image.imageUrl)));
    }

    private Map<Long, ProblemResult> fetchResultMap(List<Long> problemIds) {
        List<ProblemResult> results = queryFactory.selectFrom(problemResult)
            .where(problemResult.problem.id.in(problemIds)).fetch();

        return results.stream().collect(Collectors.toMap(r -> r.getProblem().getId(), r -> r));
    }

    private Map<Long, List<ConceptDto>> fetchConceptsMap(List<Long> problemIds) {
        List<Tuple> tuples = queryFactory.select(problemConcept.problem.id, concept.id, concept.name,
                concept.description).from(problemConcept).join(problemConcept.concept, concept)
            .where(problemConcept.problem.id.in(problemIds)).fetch();

        return tuples.stream().collect(Collectors.groupingBy(t -> t.get(problemConcept.problem.id),
            Collectors.mapping(t -> ConceptDto.of(t.get(concept.id), t.get(concept.name)), Collectors.toList())));
    }
}

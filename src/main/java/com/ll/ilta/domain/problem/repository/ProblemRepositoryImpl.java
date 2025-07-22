package com.ll.ilta.domain.problem.repository;

import static com.ll.ilta.domain.favorite.entity.QFavorite.favorite;
import static com.ll.ilta.domain.problem.entity.QConcept.concept;
import static com.ll.ilta.domain.problem.entity.QProblem.problem;
import static com.ll.ilta.domain.problem.entity.QProblemConcept.problemConcept;
import static com.ll.ilta.domain.problem.entity.QProblemResult.problemResult;
import static com.ll.ilta.domain.image.entity.QImage.image;

import com.ll.ilta.domain.problem.dto.ProblemConceptDto;
import com.ll.ilta.domain.problem.dto.ProblemResponseDto;
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
    public List<ProblemResponseDto> findProblemWithCursor(Long memberId, String afterCursor, int limit) {
        BooleanBuilder builder = new BooleanBuilder(problem.member.id.eq(memberId));
        if (afterCursor != null) {
            Cursor decoded = CursorUtil.decodeCursor(afterCursor);
            builder.and(problem.createdAt.lt(decoded.createdAt())
                .or(problem.createdAt.eq(decoded.createdAt()).and(problem.id.lt(decoded.id()))));
        }

        List<Long> problemIds = queryFactory.select(problem.id).from(problem).where(builder)
            .orderBy(problem.createdAt.desc(), problem.id.desc()).limit(limit + 1).fetch();

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
    public ProblemResponseDto findProblemById(Long problemId) {
        List<ProblemResponseDto> problems = fetchProblems(null, List.of(problemId));

        if (problems.isEmpty()) {
            return null;
        }

        Map<Long, String> imageUrlMap = fetchImageUrls(List.of(problemId));

        Map<Long, List<ProblemConceptDto>> conceptMap = fetchConceptsMap(List.of(problemId));

        ProblemResponseDto p = problems.get(0);

        return ProblemResponseDto.of(p.getId(), imageUrlMap.getOrDefault(p.getId(), null),
            conceptMap.getOrDefault(p.getId(), List.of()), p.getFavorite(), p.getOcrResult(), p.getLlmResult(),
            p.getCreatedAt());
    }

    private List<ProblemResponseDto> buildProblemDtos(Long memberId, List<Long> problemIds) {
        List<ProblemResponseDto> problems = fetchProblems(memberId, problemIds);

        Map<Long, String> imageUrlMap = fetchImageUrls(problemIds);

        Map<Long, List<ProblemConceptDto>> conceptMap = fetchConceptsMap(problemIds);

        return problems.stream().map(p -> ProblemResponseDto.of(p.getId(), imageUrlMap.getOrDefault(p.getId(), null),
            conceptMap.getOrDefault(p.getId(), List.of()), p.getFavorite(), p.getOcrResult(), p.getLlmResult(),
            p.getCreatedAt())).toList();
    }

    private List<ProblemResponseDto> fetchProblems(Long memberId, List<Long> problemIds) {
        BooleanBuilder builder = new BooleanBuilder(problem.id.in(problemIds));

        if (memberId != null) {
            builder.and(problem.member.id.eq(memberId));
        }

        // 이미지 URL은 여기서 안 불러오고 별도 메서드에서 조회
        // DTO 생성자에 null 허용 필요
        return queryFactory.select(
                Projections.constructor(ProblemResponseDto.class, problem.id, Expressions.constant("dummy"),
                    favorite.id.isNotNull(), problem.result.ocrResult, problem.result.llmResult, problem.createdAt))
            .from(problem).leftJoin(problem.result, problemResult).leftJoin(favorite)
            .on(favorite.problem.id.eq(problem.id).and(memberId != null ? favorite.member.id.eq(memberId) : null))
            .where(builder).orderBy(problem.createdAt.desc(), problem.id.desc()).fetch();
    }

    private Map<Long, String> fetchImageUrls(List<Long> problemIds) {
        List<Tuple> tuples = queryFactory.select(image.problem.id, image.imageUrl).from(image)
            .where(image.problem.id.in(problemIds)).fetch();

        return tuples.stream().collect(Collectors.toMap(t -> t.get(image.problem.id), t -> t.get(image.imageUrl)));
    }

    private Map<Long, List<ProblemConceptDto>> fetchConceptsMap(List<Long> problemIds) {
        List<ProblemConceptDto> concepts = queryFactory.select(
                Projections.constructor(ProblemConceptDto.class, problemConcept.id, problemConcept.concept.name,
                    problemConcept.problem.id)).from(problemConcept).join(problemConcept.concept, concept)
            .where(problemConcept.problem.id.in(problemIds)).fetch();

        return concepts.stream().collect(Collectors.groupingBy(ProblemConceptDto::getProblemId));
    }
}

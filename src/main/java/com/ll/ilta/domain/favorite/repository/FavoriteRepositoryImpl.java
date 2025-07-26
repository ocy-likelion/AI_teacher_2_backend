package com.ll.ilta.domain.favorite.repository;

import static com.ll.ilta.domain.favorite.entity.QFavorite.favorite;
import static com.ll.ilta.domain.image.entity.QImage.image;
import static com.ll.ilta.domain.concept.entity.QConcept.concept;
import static com.ll.ilta.domain.problem.entity.QProblem.problem;
import static com.ll.ilta.domain.problem.entity.QProblemConcept.problemConcept;
import static com.ll.ilta.domain.problem.entity.QProblemResult.problemResult;

import com.ll.ilta.domain.favorite.dto.FavoriteResponseDto;
import com.ll.ilta.domain.concept.dto.ConceptDto;
import com.ll.ilta.domain.problem.entity.ProblemResult;
import com.ll.ilta.global.common.dto.Cursor;
import com.ll.ilta.global.common.service.CursorUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FavoriteResponseDto> findFavoriteWithCursor(Long memberId, String afterCursor, int limit) {
        BooleanBuilder builder = new BooleanBuilder(favorite.member.id.eq(memberId));
        builder.and(favorite.problem.activatedAt.isNotNull());
        if (afterCursor != null) {
            Cursor decoded = CursorUtil.decodeCursor(afterCursor);
            builder.and(favorite.problem.activatedAt.lt(decoded.activatedAt())
                .or(favorite.problem.activatedAt.eq(decoded.activatedAt()).and(favorite.id.lt(decoded.id()))));
        }

        List<Long> favoriteIds = queryFactory.select(favorite.id).from(favorite).join(favorite.problem, problem)
            .where(builder).orderBy(favorite.problem.activatedAt.desc(), favorite.id.desc()).limit(limit + 1).fetch();

        boolean hasNext = favoriteIds.size() > limit;
        if (hasNext) {
            favoriteIds = favoriteIds.subList(0, limit);
        }

        if (favoriteIds.isEmpty()) {
            return List.of();
        }

        return buildFavoriteDtos(memberId, favoriteIds);
    }

    private List<FavoriteResponseDto> buildFavoriteDtos(Long memberId, List<Long> favoriteIds) {
        List<Tuple> favoriteInfos = queryFactory.select(favorite.id, favorite.problem.id, problem.activatedAt)
            .from(favorite).where(favorite.id.in(favoriteIds))
            .orderBy(favorite.problem.activatedAt.desc(), favorite.id.desc()).fetch();

        List<Long> problemIds = favoriteInfos.stream().map(t -> t.get(favorite.problem.id)).toList();

        Map<Long, String> imageUrlMap = fetchImageUrls(problemIds);
        Map<Long, ProblemResult> resultMap = fetchResultMap(problemIds);
        Map<Long, List<ConceptDto>> conceptMap = fetchConceptsMap(problemIds);

        return favoriteInfos.stream().map(info -> {
            Long favoriteId = info.get(favorite.id);
            Long problemId = info.get(favorite.problem.id);
            ProblemResult result = resultMap.get(problemId);

            return FavoriteResponseDto.of(favoriteId, problemId, imageUrlMap.getOrDefault(problemId, null),
                conceptMap.getOrDefault(problemId, List.of()), result != null ? result.getOcrResult() : null,
                result != null ? result.getLlmResult() : null, info.get(problem.activatedAt));
        }).toList();
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

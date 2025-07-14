package com.ll.ilta.domain.problem.repository;

import static com.ll.ilta.domain.problem.entity.QConcept.concept;
import static com.ll.ilta.domain.problem.entity.QProblemConcept.problemConcept;

import com.ll.ilta.domain.problem.dto.ProblemConceptDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemConceptRepository {

    private final JPAQueryFactory queryFactory;

    public Map<Long, List<ProblemConceptDto>> findConceptsByProblemIds(List<Long> problemIds) {
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

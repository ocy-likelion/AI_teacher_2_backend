package com.ll.ilta.domain.problem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemConceptDto {

    private final Long id;
    private final String name;
    private final String description;

    @JsonIgnore
    private final Long problemId;

    @Builder
    private ProblemConceptDto(Long id, String name, String description, Long problemId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.problemId = problemId;
    }

    public static ProblemConceptDto of(Long id, String name, String description, Long problemId) {
        return ProblemConceptDto.builder().id(id).name(name).description(description).problemId(problemId).build();
    }

    public ConceptDto toConceptDto() {
        return ConceptDto.of(name, description);
    }
}

package com.ll.ilta.domain.problem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemConceptDto {

    private final Long id;
    private final String name;

    @JsonIgnore
    private final Long problemId;

    @Builder
    public ProblemConceptDto(Long id, String name, Long problemId) {
        this.id = id;
        this.name = name;
        this.problemId = problemId;
    }
}

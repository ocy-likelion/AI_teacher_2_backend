package com.ll.ilta.domain.problem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemConceptDto {

    private Long id;
    private String name;

    @JsonIgnore
    private Long problemId;
}

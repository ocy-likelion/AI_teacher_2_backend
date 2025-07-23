package com.ll.ilta.domain.problem.dto;

import com.ll.ilta.domain.problem.entity.Concept;
import lombok.Getter;

@Getter
public class ConceptDto {

    private final String name;
    private final String description;

    private ConceptDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ConceptDto of(String name, String description) {
        return new ConceptDto(name, description);
    }

    public Concept toEntity() {
        return Concept.of(name, description);
    }
}

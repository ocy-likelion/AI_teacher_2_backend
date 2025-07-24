package com.ll.ilta.domain.problem.dto;

import com.ll.ilta.domain.problem.entity.Concept;
import lombok.Getter;

@Getter
public class ConceptDto {

    private final Long id;
    private final String name;
    private final String description;

    private ConceptDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static ConceptDto of(Long id, String name, String description) {
        return new ConceptDto(id, name, description);
    }

    public Concept toEntity() {
        return Concept.of(id, name, description);
    }
}

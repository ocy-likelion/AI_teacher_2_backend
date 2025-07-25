package com.ll.ilta.domain.problem.dto;

import com.ll.ilta.domain.concept.entity.Concept;
import lombok.Getter;

@Getter
public class ConceptDto {

    private final Long id;
    private final String name;

    private ConceptDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ConceptDto of(Long id, String name) {
        return new ConceptDto(id, name);
    }

    public Concept toEntity() {
        return Concept.of(id, name);
    }
}

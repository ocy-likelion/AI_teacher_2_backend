package com.ll.ilta.domain.concept.dto;

import com.ll.ilta.domain.concept.entity.Concept;
import lombok.Getter;

@Getter
public class ConceptResponseDto {

    private final Long id;
    private final String name;
    private final String description;

    private ConceptResponseDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static ConceptResponseDto from(Concept concept) {
        return new ConceptResponseDto(
            concept.getId(),
            concept.getName(),
            concept.getDescription()
        );
    }
}

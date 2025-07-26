package com.ll.ilta.domain.concept.dto;

import com.ll.ilta.domain.concept.entity.Concept;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ConceptResponseDto {

    private final Long id;
    private final String name;
    private final String description;

    @Builder
    private ConceptResponseDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static ConceptResponseDto from(Concept concept) {
        return ConceptResponseDto.builder().id(concept.getId()).name(concept.getName())
            .description(concept.getDescription()).build();
    }
}

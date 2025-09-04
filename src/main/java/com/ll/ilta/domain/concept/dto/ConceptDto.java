package com.ll.ilta.domain.concept.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConceptDto {

    private final Long id;
    private final String name;

    @Builder
    private ConceptDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ConceptDto of(Long id, String name) {
        return ConceptDto.builder().id(id).name(name).build();
    }
}

package com.ll.ilta.domain.problem.entity;

import com.ll.ilta.domain.problem.dto.ConceptDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private Concept(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Concept of(String name, String description) {
        return new Concept(name, description);
    }

    public static Concept fromDto(ConceptDto dto) {
        return Concept.of(dto.getName(), dto.getDescription());
    }
}

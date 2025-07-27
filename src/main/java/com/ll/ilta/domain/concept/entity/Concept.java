package com.ll.ilta.domain.concept.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder(access = AccessLevel.PRIVATE)
    private Concept(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Concept of(String name, String description) {
        return Concept.builder().name(name).description(description).build();
    }
}

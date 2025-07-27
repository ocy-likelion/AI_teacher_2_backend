package com.ll.ilta.domain.concept.repository;

import com.ll.ilta.domain.concept.entity.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    Optional<Concept> findByName(String name);
}

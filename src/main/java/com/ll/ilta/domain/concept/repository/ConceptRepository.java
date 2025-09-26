package com.ll.ilta.domain.concept.repository;

import com.ll.ilta.domain.concept.entity.Concept;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    Optional<Concept> findByName(String name);
}

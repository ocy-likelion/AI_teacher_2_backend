package com.ll.ilta.domain.problem.repository;

import com.ll.ilta.domain.problem.entity.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    Optional<Concept> findByName(String name);
}

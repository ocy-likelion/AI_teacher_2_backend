package com.ll.ilta.domain.concept.service;

import com.ll.ilta.domain.concept.dto.ConceptResponseDto;
import com.ll.ilta.domain.concept.entity.Concept;
import com.ll.ilta.domain.concept.repository.ConceptRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;

    public ConceptResponseDto getConcept(Long conceptId) {
        Concept concept = conceptRepository.findById(conceptId)
            .orElseThrow(() -> new EntityNotFoundException("개념을 찾을 수 없습니다: " + conceptId));

        return ConceptResponseDto.from(concept);
    }
}

package com.ll.ilta.domain.concept.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ll.ilta.domain.concept.dto.ConceptResponseDto;
import com.ll.ilta.domain.concept.service.ConceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ConceptV1Controller", description = "개념 관련 API")
@RequestMapping(value = "/api/v1/concept", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class ConceptV1Controller {

    private final ConceptService conceptService;

    @Operation(summary = "개념 조회", description = "개념 ID로 개념 정보 조회")
    @GetMapping
    public ResponseEntity<ConceptResponseDto> getConcepts(@RequestParam Long conceptId) {
        ConceptResponseDto response = conceptService.getConcept(conceptId);
        return ResponseEntity.ok(response);
    }

}

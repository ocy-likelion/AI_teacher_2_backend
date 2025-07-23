package com.ll.ilta.domain.problem.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemResponseDto {

    private final Long id;
    private final String imageUrl;
    private final List<ConceptDto> concepts;
    private final Boolean favorite;
    private final String ocrResult;
    private final String llmResult;
    private final LocalDateTime createdAt;

    private ProblemResponseDto(Long id, String imageUrl, List<ConceptDto> concepts, Boolean favorite, String ocrResult,
        String llmResult, LocalDateTime createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.concepts = concepts != null ? concepts : new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    public ProblemResponseDto(Long id, String imageUrl, Boolean favorite, String ocrResult, String llmResult,
        LocalDateTime createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.concepts = new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    public static ProblemResponseDto of(Long id, String imageUrl, List<ConceptDto> concepts, Boolean favorite,
        String ocrResult, String llmResult, LocalDateTime createdAt) {
        return new ProblemResponseDto(id, imageUrl, concepts, favorite, ocrResult, llmResult, createdAt);
    }
}

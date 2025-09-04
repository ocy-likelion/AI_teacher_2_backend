package com.ll.ilta.domain.problem.dto;

import com.ll.ilta.domain.concept.dto.ConceptDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ProblemResponseDto {

    private final Long id;
    private final String imageUrl;
    private final List<ConceptDto> concepts;
    private final Boolean favorite;
    private final String ocrResult;
    private final String summary;
    private final String explanation;
    private final LocalDateTime activatedAt;

    private ProblemResponseDto(Long id, String imageUrl, List<ConceptDto> concepts, Boolean favorite, String ocrResult,
        String summary, String explanation, LocalDateTime activatedAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.concepts = concepts != null ? concepts : new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.summary = summary;
        this.explanation = explanation;
        this.activatedAt = activatedAt;
    }

    public ProblemResponseDto(Long id, String imageUrl, Boolean favorite, String ocrResult, String summary,
        String explanation, LocalDateTime activatedAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.concepts = new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.summary = summary;
        this.explanation = explanation;
        this.activatedAt = activatedAt;
    }

    public static ProblemResponseDto of(Long id, String imageUrl, List<ConceptDto> concepts, Boolean favorite,
        String ocrResult, String summary, String explanation, LocalDateTime activatedAt) {
        return new ProblemResponseDto(id, imageUrl, concepts, favorite, ocrResult, summary, explanation, activatedAt);
    }
}

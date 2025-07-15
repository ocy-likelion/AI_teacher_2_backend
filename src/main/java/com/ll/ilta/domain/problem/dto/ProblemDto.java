package com.ll.ilta.domain.problem.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemDto {

    private final Long id;
    private final String imageUrl;
    private final List<ProblemConceptDto> concepts;
    private final Boolean favorite;
    private final String ocrResult;
    private final String llmResult;
    private final LocalDateTime createdAt;

    public ProblemDto(Long id, String imageUrl, Boolean favorite, String ocrResult, String llmResult,
        LocalDateTime createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.concepts = new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    @Builder
    private ProblemDto(Long id, String imageUrl, List<ProblemConceptDto> concepts, Boolean favorite, String ocrResult,
        String llmResult, LocalDateTime createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.concepts = concepts != null ? concepts : new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    public static ProblemDto of(Long id, String imageUrl, List<ProblemConceptDto> concepts, Boolean favorite,
        String ocrResult, String llmResult, LocalDateTime createdAt) {
        return ProblemDto.builder()
            .id(id)
            .imageUrl(imageUrl)
            .concepts(concepts != null ? concepts : List.of())
            .favorite(favorite)
            .ocrResult(ocrResult)
            .llmResult(llmResult)
            .createdAt(createdAt)
            .build();
    }
}

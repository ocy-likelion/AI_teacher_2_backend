package com.ll.ilta.domain.problem.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemDto {

    private final Long id;
    private final Long imageId;
    private final List<ProblemConceptDto> concepts;
    private final Boolean favorite;
    private final String ocrResult;
    private final String llmResult;
    private final LocalDateTime createdAt;

    private ProblemDto(Long id, Long imageId, Boolean favorite, String ocrResult, String llmResult,
        LocalDateTime createdAt) {
        this.id = id;
        this.imageId = imageId;
        this.concepts = new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    @Builder
    private ProblemDto(Long id, Long imageId, List<ProblemConceptDto> concepts, Boolean favorite, String ocrResult,
        String llmResult, LocalDateTime createdAt) {
        this.id = id;
        this.imageId = imageId;
        this.concepts = concepts != null ? concepts : new ArrayList<>();
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    public static ProblemDto of(Long id, Long imageId, List<ProblemConceptDto> concepts, Boolean favorite,
        String ocrResult, String llmResult, LocalDateTime createdAt) {
        return ProblemDto.builder()
            .id(id)
            .imageId(imageId)
            .concepts(concepts != null ? concepts : List.of())
            .favorite(favorite)
            .ocrResult(ocrResult)
            .llmResult(llmResult)
            .createdAt(createdAt)
            .build();
    }
}

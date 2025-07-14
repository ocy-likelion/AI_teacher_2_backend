package com.ll.ilta.domain.problem.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemResponseDto {
    private final Long id;
    private final Long imageId;
    private final List<ProblemConceptDto> concepts;
    private final Boolean favorite;
    private final String ocrResult;
    private final String llmResult;
    private final LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private ProblemResponseDto(Long id, Long imageId, List<ProblemConceptDto> concepts, Boolean favorite,
        String ocrResult, String llmResult, LocalDateTime createdAt) {
        this.id = id;
        this.imageId = imageId;
        this.concepts = concepts;
        this.favorite = favorite;
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    public static ProblemResponseDto of(ProblemDto problemDto, List<ProblemConceptDto> concepts) { // of 아닌가? 컨벤션
        return ProblemResponseDto.builder()
            .id(problemDto.getId())
            .imageId(problemDto.getImageId())
            .concepts(concepts)
            .favorite(problemDto.getFavorite())
            .ocrResult(problemDto.getOcrResult())
            .llmResult(problemDto.getLlmResult())
            .createdAt(problemDto.getCreatedAt())
            .build();
    }
}

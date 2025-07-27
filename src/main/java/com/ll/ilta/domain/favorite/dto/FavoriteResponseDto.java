package com.ll.ilta.domain.favorite.dto;

import com.ll.ilta.domain.concept.dto.ConceptDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FavoriteResponseDto {

    private final Long id;
    private final Long problemId;
    private final String imageUrl;
    private final List<ConceptDto> concepts;
    private final String ocrResult;
    private final String llmResult;
    private final LocalDateTime createdAt;

    @Builder
    private FavoriteResponseDto(Long id, Long problemId, String imageUrl, List<ConceptDto> concepts, String ocrResult,
        String llmResult, LocalDateTime createdAt) {
        this.id = id;
        this.problemId = problemId;
        this.imageUrl = imageUrl;
        this.concepts = concepts != null ? concepts : Collections.emptyList();
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.createdAt = createdAt;
    }

    public static FavoriteResponseDto of(Long id, Long problemId, String imageUrl, List<ConceptDto> concepts,
        String ocrResult, String llmResult, LocalDateTime createdAt) {
        return FavoriteResponseDto.builder().id(id).problemId(problemId).imageUrl(imageUrl).concepts(concepts)
            .ocrResult(ocrResult).llmResult(llmResult).createdAt(createdAt).build();
    }
}

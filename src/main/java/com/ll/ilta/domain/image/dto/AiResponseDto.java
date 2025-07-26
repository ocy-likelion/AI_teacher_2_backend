package com.ll.ilta.domain.image.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.ilta.domain.concept.entity.Concept;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AiResponseDto {

    @JsonProperty("summary")
    private final String ocrResult;

    @JsonProperty("explanation")
    private final String llmResult;

    @JsonProperty("concept_tags")
    private final List<Concept> conceptTags;

    @Builder
    private AiResponseDto(String ocrResult, String llmResult, List<Concept> conceptTags) {
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.conceptTags = conceptTags;
    }

    public static AiResponseDto of(String ocrResult, String llmResult, List<Concept> conceptTags) {
        return AiResponseDto.builder().ocrResult(ocrResult).llmResult(llmResult).conceptTags(conceptTags).build();
    }
}

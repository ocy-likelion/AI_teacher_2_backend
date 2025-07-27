package com.ll.ilta.domain.image.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.ilta.domain.concept.entity.Concept;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AiResponseDto {

    @JsonProperty("ocr_result")
    private final String ocrResult;

    @JsonProperty("summary")
    private final String summary;

    @JsonProperty("explanation")
    private final String explanation;

    @JsonProperty("concept_tags")
    private final List<Concept> conceptTags;

    @Builder
    private AiResponseDto(String ocrResult, String summary, String explanation, List<Concept> conceptTags) {
        this.ocrResult = ocrResult;
        this.summary = summary;
        this.explanation = explanation;
        this.conceptTags = conceptTags;
    }

    public static AiResponseDto of(String ocrResult, String summary, String explanation, List<Concept> conceptTags) {
        return AiResponseDto.builder().ocrResult(ocrResult).summary(summary).explanation(explanation)
            .conceptTags(conceptTags).build();
    }
}

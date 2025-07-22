package com.ll.ilta.domain.image.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.ilta.domain.problem.dto.ConceptDto;
import java.util.List;
import lombok.Getter;

@Getter
public class AiResponseDto {

    @JsonProperty("ocr_text")
    private final String ocrResult;

    @JsonProperty("explanation")
    private final String llmResult;

    @JsonProperty("concept_tags")
    private final List<ConceptDto> conceptTags;

    private AiResponseDto(String ocrResult, String llmResult, List<ConceptDto> conceptTags) {
        this.ocrResult = ocrResult;
        this.llmResult = llmResult;
        this.conceptTags = conceptTags;
    }

    public static AiResponseDto of(String ocrResult, String llmResult, List<ConceptDto> concepts) {
        return new AiResponseDto(ocrResult, llmResult, concepts);
    }
}

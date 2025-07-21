package com.ll.ilta.domain.problem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MathExplainResponseDto {

    @JsonProperty("ocr_text")
    private String ocrResult;

    @JsonProperty("explanation")
    private String llmResult;

    @JsonProperty("concept_tags")
    private List<ConceptTagDto> conceptTags;

    @Getter
    @NoArgsConstructor
    public static class ConceptTagDto {
        private String name;
        private String description;
    }
}

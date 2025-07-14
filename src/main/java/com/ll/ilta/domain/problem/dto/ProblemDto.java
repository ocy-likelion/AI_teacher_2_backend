package com.ll.ilta.domain.problem.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProblemDto {

    private Long id;
    private Long imageId;
    private Boolean favorite;
    private String ocrResult;
    private String llmResult;
    private LocalDateTime createdAt;

}

package com.ll.ilta.domain.problem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProblemResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ocrResult;
    private String llmResult;
    private Boolean status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false, unique = true)
    private Problem problem;

    public static ProblemResult of(String ocrResult, String llmResult, Boolean status, Problem problem) {
        ProblemResult result = new ProblemResult();
        result.ocrResult = ocrResult;
        result.llmResult = llmResult;
        result.status = status;
        result.problem = problem;
        return result;
    }
}

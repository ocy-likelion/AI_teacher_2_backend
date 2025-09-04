package com.ll.ilta.domain.problem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProblemResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String ocrResult;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(columnDefinition = "text")
    private String explanation;

    private Boolean status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false, unique = true)
    private Problem problem;

    @Builder(access = AccessLevel.PRIVATE)
    private ProblemResult(String ocrResult, String summary, String explanation, Boolean status, Problem problem) {
        this.ocrResult = ocrResult;
        this.summary = summary;
        this.explanation = explanation;
        this.status = status;
        this.problem = problem;
    }

    public static ProblemResult of(String ocrResult, String summary, String explanation, Boolean status,
        Problem problem) {
        return ProblemResult.builder().ocrResult(ocrResult).summary(summary).explanation(explanation).status(status)
            .problem(problem).build();
    }
}

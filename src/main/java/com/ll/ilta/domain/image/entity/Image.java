package com.ll.ilta.domain.image.entity;

import com.ll.ilta.domain.problem.entity.Problem;
import jakarta.persistence.Entity;
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
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "problem_id", nullable = false, unique = true)
    private Problem problem;

    @Builder(access = AccessLevel.PRIVATE)
    private Image(String imageUrl, Problem problem) {
        this.imageUrl = imageUrl;
        this.problem = problem;
    }

    public static Image of(String imageUrl, Problem problem) {
        return Image.builder().imageUrl(imageUrl).problem(problem).build();
    }
}

package com.ll.ilta.domain.favorite.entity;

import com.ll.ilta.domain.member.v2.entity.Member;
import com.ll.ilta.domain.problem.entity.Problem;
import com.ll.ilta.global.jpa.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Favorite extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "problem_id", nullable = false, unique = true)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder(access = AccessLevel.PRIVATE)
    private Favorite(Problem problem, Member member) {
        this.problem = problem;
        this.member = member;
    }

    public static Favorite of(Problem problem, Member member) {
        return Favorite.builder().problem(problem).member(member).build();
    }
}

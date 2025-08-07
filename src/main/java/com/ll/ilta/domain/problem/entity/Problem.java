package com.ll.ilta.domain.problem.entity;

import com.ll.ilta.domain.member.v2.entity.Member;
import com.ll.ilta.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Problem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = true)
    private LocalDateTime activatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Problem(Member member) {
        this.member = member;
    }

    public static Problem from(Member member) {
        return Problem.builder().member(member).build();
    }

    public void activate() {
        this.activatedAt = LocalDateTime.now();
    }

    public boolean isActivated() {
        return this.activatedAt != null;
    }

}

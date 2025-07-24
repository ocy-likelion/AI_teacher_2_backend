package com.ll.ilta.domain.problem.entity;

import com.ll.ilta.domain.member.v1.entity.MemberV1;
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
    private MemberV1 memberV1;

    @Column(nullable = true)
    private LocalDateTime activatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Problem(MemberV1 member) {
        this.memberV1 = memberV1;
    }

    public static Problem from(MemberV1 memberV1) {
        return Problem.builder().memberV1(memberV1).build();
    }

    public void activate() {
        this.activatedAt = LocalDateTime.now();
    }

    public boolean isActivated() {
        return this.activatedAt != null;
    }

}

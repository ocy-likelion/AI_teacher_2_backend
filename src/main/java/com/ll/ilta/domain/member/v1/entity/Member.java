package com.ll.ilta.domain.member.v1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String username;  // 로그인용 ID
    private String password;  // 로그인 비밀번호
    private String name;      // 이름 (자녀 이름 가능)
    private Integer grade;    // 자녀 학년

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Member(String username, String password, String name, Integer grade) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.grade = grade;
    }

}

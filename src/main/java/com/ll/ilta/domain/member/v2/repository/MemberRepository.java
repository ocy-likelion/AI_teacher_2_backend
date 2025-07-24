package com.ll.ilta.domain.member.v2.repository;


import com.ll.ilta.domain.member.v2.dto.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}

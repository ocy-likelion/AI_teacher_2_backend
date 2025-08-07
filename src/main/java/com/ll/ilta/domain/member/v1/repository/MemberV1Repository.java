package com.ll.ilta.domain.member.v1.repository;

import com.ll.ilta.domain.member.v1.entity.MemberV1;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberV1Repository extends JpaRepository<MemberV1, Long> {

    Optional<MemberV1> findByUsername(String username);
}

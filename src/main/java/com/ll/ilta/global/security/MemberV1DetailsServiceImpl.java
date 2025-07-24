package com.ll.ilta.global.security;

import com.ll.ilta.domain.member.v1.entity.MemberV1;
import com.ll.ilta.domain.member.v1.repository.MemberV1Repository;
import com.ll.ilta.domain.member.v2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberV1DetailsServiceImpl implements UserDetailsService {

    private final MemberV1Repository memberv1Repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberV1 memberV1 = memberv1Repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new MemberV1DetailsImpl(memberV1);
    }
}

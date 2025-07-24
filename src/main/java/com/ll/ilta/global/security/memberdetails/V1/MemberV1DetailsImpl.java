package com.ll.ilta.global.security.memberdetails.V1;

import com.ll.ilta.domain.member.v1.entity.MemberV1;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record MemberV1DetailsImpl(MemberV1 memberV1) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return memberV1.getPassword();
    }

    @Override
    public String getUsername() {
        return memberV1.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return memberV1.getId();
    }

    public String getName() {
        return memberV1.getName();
    }

    public Integer getGrade() {
        return memberV1.getGrade();
    }
}

package com.ll.ilta.domain.member.v2.service;

import com.ll.ilta.domain.member.v2.entity.Member;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    Member oAuthLogin(String accessCode, HttpServletResponse httpServletResponse);
}

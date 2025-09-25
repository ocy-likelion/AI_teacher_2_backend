package com.ll.ilta.domain.member.service;

import com.ll.ilta.domain.member.dto.response.MemberResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    MemberResponseDTO.JoinResultDTO  oAuthLogin(String accessCode, HttpServletResponse httpServletResponse);
}

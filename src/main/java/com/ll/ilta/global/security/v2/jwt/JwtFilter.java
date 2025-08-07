package com.ll.ilta.global.security.v2.jwt;

import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
import com.ll.ilta.global.security.common.SecurityConstants;
import com.ll.ilta.global.security.v2.member.PrincipalDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final PrincipalDetailsService principalDetailsService;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return Arrays.stream(SecurityConstants.allowedUrls)
            .anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String accessToken = jwtUtil.resolveAccessToken(request);

        if (jwtUtil.isTokenValid(accessToken)) {
            String email = jwtUtil.getEmail(accessToken);
            log.info("[JwtFilter] 이메일로 유저 조회 시도: {}", email);
            UserDetails userDetails = principalDetailsService.loadUserByUsername(email);
            log.info("[JwtFilter] 유저 조회 성공: {}", userDetails.getUsername());

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.info("[JwtFilter] SecurityContextHolder에 인증 객체 설정 완료 ✅");
                log.info("[JwtFilter] 필터 실행됨 ✅");
            } else {
                throw new AuthHandler(ErrorStatus.NOT_FOUND_USER);
            }
        } else {
            throw new AuthHandler(ErrorStatus.AUTH_INVALID_TOKEN);
        }

    }
}

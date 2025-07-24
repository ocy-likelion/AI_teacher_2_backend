package com.ll.ilta.global.security.filter;

import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
import com.ll.ilta.global.security.constant.SecurityConstants;
import com.ll.ilta.global.security.memberdetails.V2.PrincipalDetailsService;
import com.ll.ilta.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

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
            UserDetails userDetails = principalDetailsService.loadUserByUsername(email);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new AuthHandler(ErrorStatus.NOT_FOUND_USER);
            }
        } else {
            throw new AuthHandler(ErrorStatus.AUTH_INVALID_TOKEN);
        }

    }
}

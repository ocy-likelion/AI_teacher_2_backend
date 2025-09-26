package com.ll.ilta.global.security.config;

import com.ll.ilta.global.security.exception.JwtAccessDeniedHandler;
import com.ll.ilta.global.security.exception.JwtExceptionFilter;
import com.ll.ilta.global.security.jwt.JwtFilter;
import com.ll.ilta.global.security.jwt.JwtUtil;
import com.ll.ilta.global.security.member.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final PrincipalDetailsService principalDetailsService;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/v2/**");

        // cors disable
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // csrf disable
        http.csrf(AbstractHttpConfigurer::disable);

        // form 로그인 방식 disable
        http.formLogin(AbstractHttpConfigurer::disable);

        // http basic 인증 방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        // Session Stateless하게 관리
        http.sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 경로별 인가
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
            // ✅ 공용 허용 경로 (비로그인)
            .requestMatchers(SecurityConstants.allowedUrls).permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v2/members").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v2/oauth").permitAll()

            // ✅ 로그인 사용자 (USER, ADMIN)
            .requestMatchers(HttpMethod.GET, "/api/v2/concepts").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/v2/favorites/list").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v2/favorites").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v2/images/upload").hasAnyRole("USER", "ADMIN")

            .requestMatchers(HttpMethod.GET, "/api/v2/members/me/profile").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/v2/members/me/profile").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/v2/members/me/profile").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/v2/members/me/child-info/exist").hasAnyRole("USER", "ADMIN")

            .requestMatchers(HttpMethod.POST, "/api/v2/members/{memberId}/posts").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/v2/posts/{postId}").hasAnyRole("USER", "ADMIN")

            // ✅ 관리자 전용
            .requestMatchers(HttpMethod.GET, "/api/v2/members/admin/all").hasRole("ADMIN")

            // ✅ 나머지는 인증 필요
            .anyRequest().authenticated()
        );

        http.exceptionHandling(
            (configurer ->
                configurer
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            )
        );

        http.addFilterBefore(new JwtFilter(jwtUtil, principalDetailsService),
            UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtExceptionFilter(), JwtFilter.class);

        return http.build();
    }
}

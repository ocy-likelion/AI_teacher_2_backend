package com.ll.ilta.global.config;

import com.ll.ilta.global.security.constant.SecurityConstants;
import com.ll.ilta.global.security.filter.CustomDaoAuthenticationProvider;
import com.ll.ilta.global.security.filter.JwtAccessDeniedHandler;
import com.ll.ilta.global.security.filter.JwtExceptionFilter;
import com.ll.ilta.global.security.filter.JwtFilter;
import com.ll.ilta.global.security.filter.LoginFilter;
import com.ll.ilta.global.security.memberdetails.V2.PrincipalDetailsService;
import com.ll.ilta.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigV2 {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final PrincipalDetailsService principalDetailsService;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // PasswordEncoerder Been
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CustomDaoAuthenticationProvider customDaoAuthenticationProvider() {
        CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
        provider.setUserDetailsService(principalDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 우선순위 2번 (v1 다음)
    @Bean
    @Order(2)
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
            .requestMatchers(HttpMethod.POST, "/api/v2/members").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v2/members/{memberId}/posts").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/v2/posts/{postId}").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v2/members/{memberId}/posts/{postId}/replies").hasAnyRole("ADMIN")
            .requestMatchers(SecurityConstants.allowedUrls).permitAll()
            .anyRequest().authenticated()
        );

        http.exceptionHandling(
            (configurer ->
                configurer
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            )
        );

        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
            UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtFilter(jwtUtil, principalDetailsService),
            UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtExceptionFilter(), JwtFilter.class);

        return http.build();
    }
}

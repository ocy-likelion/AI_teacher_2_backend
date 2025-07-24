package com.ll.ilta.global.config;

import com.ll.ilta.global.security.JwtAuthenticationFilter;
import com.ll.ilta.global.security.JwtTokenProvider;
import com.ll.ilta.global.security.memberdetails.V1.MemberV1DetailsServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigV1 {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberV1DetailsServiceImpl memberV1DetailsService;

    @Value("${app.allowed-origins}")
    private String allowedOrigins;

    @Order(1)
    @Bean(name = "securityFilterChainV1")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
            .securityMatcher("/api/v1/**")   // v1 경로만 처리
            .formLogin(form -> form.disable()) // 기본 로그인 폼 비활성화
            .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()
//                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .requestMatchers("/api/v1/member/login").permitAll() // 로그인은 모두 허용
//                .requestMatchers("/api/v1/image/upload").permitAll() // TODO: JWT 검사 제외로 임시 방편, 추후 제거해야 함
//                .anyRequest().authenticated()
            ).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, memberV1DetailsService);
    }

    @Bean("corsConfigurationSourceV1")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        List<String> originsList = List.of(allowedOrigins.split(","));
        config.setAllowedOrigins(originsList);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean("passwordEncoderV1")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("authenticationManagerV1")
    public AuthenticationManager authenticationManagerV1(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(memberV1DetailsService).passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}

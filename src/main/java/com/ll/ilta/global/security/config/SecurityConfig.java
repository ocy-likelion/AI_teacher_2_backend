package com.ll.ilta.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .formLogin().disable() // 기본 로그인 폼 제거
            .httpBasic().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/users/login").permitAll() // 로그인 API만 허용
                .anyRequest().permitAll() // 나머지도 모두 허용 (MVP 단계용)
            );

        return http.build();
    }
}

}

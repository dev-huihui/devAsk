package com.api.devask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        // 2026.04.13 securityFilterChain 빈 추가
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                // JWT 사용 시 세션을 사용하지 않으므로 기본 폼 로그인 등은 비활성화하는 것이 일반적입니다.
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/auth/**", "/login", "/css/**", "/js/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form.disable())
                                .httpBasic(basic -> basic.disable());

                return http.build();
        }
}

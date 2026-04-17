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
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // 2026.04.13 csrf 비활성화(JWT를 사용할 것이기 때문)
                http.csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/auth/**",
                                                                "/css/**",
                                                                "/js/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login"));

                return http.build();
        }

        // 2026.04.17 filterChain 빈 추가
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.csrf().disable()
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/login").permitAll()
                                                .anyRequest().authenticated());

                return http.build();
        }

}

package com.techeer.checkIt.global.config;

import com.techeer.checkIt.global.jwt.JwtAuthenticationFilter;
import com.techeer.checkIt.global.jwt.JwtTokenProvider;
import com.techeer.checkIt.global.jwt.exception.JwtAccessDeniedHandler;
import com.techeer.checkIt.global.jwt.exception.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.csrf().disable().cors().and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 401 (인증되지 않은 사용자)
            .accessDeniedHandler(jwtAccessDeniedHandler)            // 403 (권한이 없는 사용자)
            .and()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // Security는 기본적으로 세션 사용하지만, jwt 사용할 것이기 때문에 STATELESS 로 설정
            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/users/join", "/api/v1/users/login", "/api/v1/users/duplicated/**",
                                    "/api/v1/books/search", "/api/v1/books/like", "/api/v1/books/new").permitAll() // 회원가입, 로그인, 검색 API는 인증 없이 허용
            .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
                    UsernamePasswordAuthenticationFilter.class);                                // Security 로직에 필터 등록
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

package com.techeer.checkIt.domain.user.service;

import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import com.techeer.checkIt.global.jwt.JwtToken;
import com.techeer.checkIt.global.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.techeer.checkIt.fixture.UserFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DataRedisTest
//@TestPropertySource(properties = "spring.redis.host=localhost")
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("Service) 로그인")
    void login() {
        User user = TEST_LOGIN_USER;

        Authentication auth = Mockito.mock(Authentication.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(TEST_USER_LOGIN_REQ.getUsername(), TEST_USER_LOGIN_REQ.getPassword());

        when(userRepository.findUserByUsername(TEST_USER_LOGIN_REQ.getUsername())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        when(authenticationManager.authenticate(authenticationToken)).thenReturn(auth);  // 변경부분
        when(jwtTokenProvider.generateToken(any())).thenReturn(TEST_JWT);

//        redisTemplate.opsForValue().set("RT:test" + user.getUsername(), TEST_JWT.getRefreshToken());

        // when
        JwtToken response = authService.login(TEST_USER_LOGIN_REQ.getUsername(), TEST_USER_LOGIN_REQ.getPassword());

        // then
//        verify(userRepository, times(1)).findUserByUsername(TEST_USER_LOGIN_REQ.getUsername());
        assertThat(response.getAccessToken()).isEqualTo(TEST_JWT.getAccessToken());
        // 예상 결과와 실제 결과 비교

        verify(redisTemplate, times(1)).opsForValue().set(eq(TEST_JWT.getRefreshToken()), any(), anyLong(), eq(TimeUnit.MILLISECONDS));
    }


    @Test
    void logout() {
    }
}
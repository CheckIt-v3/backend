package com.techeer.checkIt.domain.user.service;

import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.mapper.UserMapper;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.techeer.checkIt.fixture.UserFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Service) 아이디 중복확인")
    void isDuplicatedUsername() {
        String username = "test";

        when(userRepository.existsByUsername(username)).thenReturn(true);
        boolean isDuplicated = userService.isDuplicatedUsername(username);

        assertThat(isDuplicated).isEqualTo(true);
    }

    @Test
    @DisplayName("Service) 회원가입")
    void join() {
        User user = TEST_USER2;
        when(userMapper.toEntity(TEST_USER_JOIN_REQ)).thenReturn(user);
        user.setEncryptedPassword(passwordEncoder);

        userService.join(TEST_USER_JOIN_REQ);

        assertThat(user.getNickname()).isEqualTo(TEST_USER_JOIN_REQ.getNickname());
    }

    @Test
    @DisplayName("Service) id별 회원 조회")
    void findUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(TEST_USER));

        User user = userService.findUserById(1L);

        assertThat(user.getNickname()).isEqualTo(TEST_USER.getNickname());
    }
}
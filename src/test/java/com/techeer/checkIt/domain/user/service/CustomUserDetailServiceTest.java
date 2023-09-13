package com.techeer.checkIt.domain.user.service;

import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {
    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Service) username 으로 UserDetails 객체 반환")
    void loadUserByUsername() {
        // given
        User user = TEST_USER;

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.ofNullable(user));
        UserDetails userDetail = customUserDetailService.loadUserByUsername(user.getUsername());

        assertThat(userDetail.getUsername()).isEqualTo(user.getUsername());
    }
}
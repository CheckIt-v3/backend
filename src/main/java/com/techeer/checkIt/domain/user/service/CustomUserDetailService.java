package com.techeer.checkIt.domain.user.service;

import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import com.techeer.checkIt.domain.user.exception.UserNotFoundException;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Cacheable("users")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername called for username: {}", username);
        User principal = userRepository.findUserByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return new UserDetail(principal);
    }
}

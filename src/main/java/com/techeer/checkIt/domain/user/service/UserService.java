package com.techeer.checkIt.domain.user.service;

import com.techeer.checkIt.domain.user.dto.request.UserJoinReq;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.exception.UserNotFoundException;
import com.techeer.checkIt.domain.user.mapper.UserMapper;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public boolean isDuplicatedUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public void join(UserJoinReq userJoinReq) {
        User user = userMapper.toEntity(userJoinReq);
        user.setEncryptedPassword(passwordEncoder);
        userRepository.save(user);
    }

    public User findUserById(Long uid) {
        return userRepository.findById(uid).orElseThrow(UserNotFoundException::new);
    }
}

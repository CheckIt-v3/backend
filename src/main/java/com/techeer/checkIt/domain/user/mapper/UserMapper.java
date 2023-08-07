package com.techeer.checkIt.domain.user.mapper;

import com.techeer.checkIt.domain.user.dto.request.UserJoinReq;
import com.techeer.checkIt.domain.user.entity.Role;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserJoinReq userJoinReq) {
        return User.builder()
                .username(userJoinReq.getUsername())
                .password(userJoinReq.getPassword())
                .nickname(userJoinReq.getNickname())
                .role(Role.USER)
                .build();
    }
}

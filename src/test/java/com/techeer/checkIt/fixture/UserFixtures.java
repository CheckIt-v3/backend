package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.user.dto.request.UserJoinReq;
import com.techeer.checkIt.domain.user.dto.request.UserLoginReq;
import com.techeer.checkIt.domain.user.entity.Role;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.global.jwt.JwtToken;

public class UserFixtures {
    public static final UserJoinReq TEST_USER_JOIN_REQ =
            UserJoinReq.builder()
                    .username("test3")
                    .password("@Test123")
                    .nickname("테스트3")
                    .build();

    public static final UserLoginReq TEST_USER_LOGIN_REQ =
            UserLoginReq.builder()
                    .username("test")
                    .password("@Test123")
                    .build();

    public static final JwtToken TEST_JWT =
            JwtToken.builder()
                    .grantType("Bearer")
                    .accessToken("access 토큰")
                    .refreshToken("refresh 토큰")
                    .build();

    public static final User TEST_USER =
            User.builder()
                    .username("test3")
                    .password("@Test123")
                    .nickname("테스트3")
                    .role(Role.USER)
                    .build();

    public static final User TEST_USER2 =
            User.builder()
                    .username("test2")
                    .password("@Test123")
                    .nickname("테스트2")
                    .role(Role.USER)
                    .build();
}
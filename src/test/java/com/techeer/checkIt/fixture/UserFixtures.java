package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.user.entity.User;

public class UserFixtures {
    public static final User TEST_USER =
            User.builder()
                    .id(1L)
                    .build();
}
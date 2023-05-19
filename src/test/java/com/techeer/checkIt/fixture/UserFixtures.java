package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.user.entity.User;

public class UserFixtures {
    public static final User TEST_USER =
            User.builder()
                    .id(1L)
                    .build();

    public static final User TEST_USER2 =
            User.builder()
                    .id(2L)
                    .build();
}
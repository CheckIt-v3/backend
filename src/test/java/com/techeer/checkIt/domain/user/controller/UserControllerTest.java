package com.techeer.checkIt.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.checkIt.domain.user.service.LoginService;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static com.techeer.checkIt.fixture.UserFixtures.*;
import static com.techeer.checkIt.global.result.ResultCode.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private LoginService loginService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(applicationContext)
                        .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                        .build();
    }
    private String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    @DisplayName("Controller) 회원가입")
    void join() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(TEST_USER_JOIN_REQ)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(USER_REGISTRATION_SUCCESS))))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller) 아이디 중복확인")
    void isDuplicatedUsername() throws Exception {
        String username = "test";

        when(userService.isDuplicatedUsername(username))
                .thenReturn(true)
                .thenReturn(false);


        mockMvc.perform(get("/api/v1/users/duplicated/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(USER_USERNAME_DUPLICATED, true))))
                .andDo(print());

        mockMvc.perform(get("/api/v1/users/duplicated/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(USER_USERNAME_NOT_DUPLICATED, false))))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller) 로그인")
    void login() throws Exception {
        when(loginService.login("test", "@Test123")).thenReturn(TEST_JWT);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(TEST_USER_LOGIN_REQ)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(USER_LOGIN_SUCCESS, TEST_JWT))))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller) 로그아웃")
    void logout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(TEST_JWT)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(USER_LOGOUT_SUCCESS))))
                .andDo(print());
    }
}
package com.techeer.checkIt.domain.reading.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.service.ReadingService;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOK;
import static com.techeer.checkIt.fixture.ReadingFixtures.TEST_READING;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static com.techeer.checkIt.global.result.ResultCode.READING_CREATE_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.READING_PERCENTAGE_SUCCESS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest
public class ReadingControllerTest {
    @MockBean
    private BookService bookService;
    @MockBean
    private UserService userService;
    @MockBean
    private ReadingService readingService;
    @MockBean
    private ReadingVolumeService readingVolumeService;
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
    @DisplayName("내 서재의 책을 등록한다.")
    void createReading() throws Exception {
//        when(userService.findUserById(any())).thenReturn(TEST_USER);
//        when(bookService.findBookById(any())).thenReturn(TEST_BOOK);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/v1/readings/{uid}",1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJsonString(TEST_READING)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(READING_CREATE_SUCCESS))))
                .andDo(print());
    }

    @Test
    @DisplayName("책을 얼만큼 읽었는지 퍼센트를 계산해준다.")
    void getPercentage() throws Exception {
        when(userService.findUserById(any())).thenReturn(TEST_USER);
        when(bookService.findBookById(any())).thenReturn(TEST_BOOK);
//        when(readingService.findReadingByUserAndBook(any(),any())).thenReturn(TEST_READING);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/readings/percentages/{uid}",1L)
                                .param("bid","1"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(READING_PERCENTAGE_SUCCESS))))
                .andDo(print());
    }
}
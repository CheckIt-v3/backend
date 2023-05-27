package com.techeer.checkIt.domain.reading.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.service.ReadingService;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultCode;
import com.techeer.checkIt.global.result.ResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.techeer.checkIt.domain.reading.entity.ReadingStatus.READING;
import static com.techeer.checkIt.fixture.BookFixtures.*;
import static com.techeer.checkIt.fixture.ReadingFixtures.TEST_UPDATE_READ_REQ;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class ReadingControllerTest {
    @MockBean
    private BookService bookService;
    @MockBean
    private UserService userService;
    @MockBean
    private ReadingService readingService;
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
    @DisplayName("Controller) 상태 별 책 조회한다.")
    void getReadingByStatus() throws Exception {
        // given
        List<BookRes> readingList  = new ArrayList<>();
        readingList.add(TEST_BOOK);

        // when
        when(userService.findUserById(1L)).thenReturn(TEST_USER);
        when(readingService.findReadingByStatus(TEST_USER.getId(), READING)).thenReturn(readingList);

        // then
        mockMvc.perform(get("/api/v1/readings/{uid}", 1L)
                        .queryParam("status", "READING"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(readingList)))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller) 독서 상태 변경한다.")
    void createReading() throws Exception {
        // when
        when(userService.findUserById(1L)).thenReturn(TEST_USER);
        when(bookService.findById(1L)).thenReturn(BOOK_ENT);

        // then
        mockMvc.perform(put("/api/v1/readings/status/{uid}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("status", "READING")
                        .content(toJsonString(TEST_UPDATE_READ_REQ)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(ResultCode.READING_STATUS_UPDATE_SUCCESS))))
                .andDo(print());
    }
}
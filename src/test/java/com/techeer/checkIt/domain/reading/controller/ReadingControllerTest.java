package com.techeer.checkIt.domain.reading.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.reading.service.ReadingService;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import com.techeer.checkIt.domain.user.entity.User;
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

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOKENT;
import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOKENT2;
import static com.techeer.checkIt.fixture.ReadingFixtures.TEST_READINGREQ;
import static com.techeer.checkIt.fixture.ReadingVolumeFixtures.*;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER2;
import static com.techeer.checkIt.global.result.ResultCode.*;
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
    @MockBean
    private ReadingRepository readingRepository;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    static double percentage;
    static User user;
    static Book book;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(applicationContext)
                        .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                        .build();
        user = userService.findUserById(1L);
        book = bookService.findById(1L);
        percentage = readingService.findReadingByUserAndBook(user, book);
    }
    private String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    @DisplayName("내 서재의 책을 등록한다.")
    void createReading() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/v1/readings/{uid}",1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJsonString(TEST_READINGREQ)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(READING_CREATE_SUCCESS))))
                .andDo(print());
    }

    @Test
    @DisplayName("책을 얼만큼 읽었는지 퍼센트를 계산해준다.")
    void getPercentage() throws Exception {
        when(userService.findUserById(1L)).thenReturn(TEST_USER);
        when(bookService.findById(1L)).thenReturn(TEST_BOOKENT);
        when(userService.findUserById(2L)).thenReturn(TEST_USER2);
        when(bookService.findById(2L)).thenReturn(TEST_BOOKENT2);
        when(readingService.findReadingByUserAndBook(TEST_USER,TEST_BOOKENT)).thenReturn(TEST_READINGVOLUME);
        when(readingService.findReadingByUserAndBook(TEST_USER2,TEST_BOOKENT2)).thenReturn(TEST_READINGVOLUME2);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/readings/percentages/{uid}",1L)
                                .param("bookId","1"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(READING_PERCENTAGE_SUCCESS, TEST_READINGVOLUME))))
                .andDo(print());
    }

    @Test
    @DisplayName("마지막으로 읽은 페이지를 수정해준다.")
    void updateReadingAndReadingVolume() throws Exception {
        when(userService.findUserById(1L)).thenReturn(TEST_USER);
        when(bookService.findById(TEST_READINGVOLUME_UPDATE_REQ.getBookId())).thenReturn(TEST_BOOKENT);
        when(readingService.updateReadingAndReadingVolume(any(), any(), any())).thenReturn(TEST_READINGVOLUME_UPDATE_RES);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/v1/readings/{uid}",1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJsonString(TEST_READINGVOLUME_UPDATE_REQ)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(READING_UPDATE_SUCCESS, TEST_READINGVOLUME_UPDATE_RES))))
                .andDo(print());
    }
}
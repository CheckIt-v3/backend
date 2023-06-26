package com.techeer.checkIt.domain.readingVolume.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.techeer.checkIt.fixture.ReadingVolumeFixtures.*;
import static com.techeer.checkIt.fixture.UserFixtures.*;
import static com.techeer.checkIt.global.result.ResultCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class ReadingVolumeControllerTest {

    @MockBean
    private ReadingVolumeService readingVolumeService;
    @MockBean
    private UserService userService;
    @MockBean
    private BookService bookService;
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
    @DisplayName("일주일동안의 독서량을 보여준다")
    void searchReadingVolumes() throws Exception{
        when(userService.findUserById(1L)).thenReturn(TEST_USER);
        when(userService.findUserById(2L)).thenReturn(TEST_USER2);
        when(readingVolumeService.findReadingVolumesByUserAndDateBetween(any())).thenReturn(List.of(TEST_SEARCH_READINGVOLUME_RES));

        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/readingvolumes/{uid}",1L))
                .andExpect(status().isOk())
                .andExpect(content().string(toJsonString(ResultResponse.of(GET_READING_VOLUMES_SUCCESS,List.of(TEST_SEARCH_READINGVOLUME_RES)))))
                .andDo(print());
    }
}
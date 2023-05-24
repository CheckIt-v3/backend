package com.techeer.checkIt.domain.book.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOK;
import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOK2;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(applicationContext)
                        .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                        .build();
    }

    @Test
    @DisplayName("Controller) 책 제목으로 전체 책 검색")
    void searchTitle() throws Exception {
        List<BookRes> books = new ArrayList<>();
        books.add(TEST_BOOK);

        String title = "원";

        when(bookService.findBookByTitle(title)).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/search")
                        .queryParam("title",title))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("원씽"))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller) 책 id로 조회")
    void getBookById() throws Exception {

        when(bookService.findBookById(1L)).thenReturn(TEST_BOOK);
        when(bookService.findBookById(2L)).thenReturn(TEST_BOOK2);

        mockMvc.perform(get("/api/v1/books/{bookId}", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
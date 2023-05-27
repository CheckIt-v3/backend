package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.mapper.BookMapper;
import com.techeer.checkIt.domain.book.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.techeer.checkIt.fixture.BookFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("Service) 책 제목으로 검색")
    void findBookByTitle() {
        // given
        List<Book> books = new ArrayList<>();
        books.add(TEST_BOOK_ENT); // 세이노

        List<BookRes> bookList = new ArrayList<>();
        bookList.add(TEST_BOOK);
        String title = "세이노";

        given(bookRepository.findByTitle(any())).willReturn(books);
        when(bookMapper.toDtoList(books)).thenReturn(bookList);

        // when
        bookList = bookService.findBookByTitle(title);


        // then
        assertThat(bookList.size()).isEqualTo(1);

    }

    @Test
    @DisplayName("Service) book id로 책 조회")
    void findBookById() {
        // given
        BookRes bookRes = TEST_BOOK;

        given(bookRepository.findByBookId(1L)).willReturn(Optional.ofNullable(TEST_BOOK_ENT));
        when(bookMapper.toDto(TEST_BOOK_ENT)).thenReturn(bookRes);

        // when
        bookRes = bookService.findBookById(1L);

        // then
        assertThat(bookRes.getTitle()).isEqualTo(TEST_BOOK_ENT.getTitle());
    }

    @Test
    @DisplayName("Service) id별 판별")
    void findById() {
        // given
        given(bookRepository.findById(1L)).willReturn(Optional.ofNullable(TEST_BOOK_ENT));

        // when
        Book book = bookService.findById(1L);

        // then
        assertThat(book.getTitle()).isEqualTo(TEST_BOOK_ENT.getTitle());
    }
}
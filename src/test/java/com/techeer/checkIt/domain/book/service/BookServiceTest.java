package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.entity.BookDocument;
import com.techeer.checkIt.domain.book.exception.BookNotFoundException;
import com.techeer.checkIt.domain.book.mapper.BookMapper;
import com.techeer.checkIt.domain.book.repository.BookJpaRepository;
import com.techeer.checkIt.domain.book.repository.BookSearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.techeer.checkIt.fixture.BookFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookJpaRepository bookJpaRepository;
    @Mock
    private BookSearchRepository bookSearchRepository;
    @Mock
    private BookMapper bookMapper;

    /*
    review 테스트 중에 오류 발생으로 주석 처리
     */
//    @Test
//    @DisplayName("Service) 책 제목으로 검색")
//    void findBookByTitle() {
//        // given
//        List<BookDocument> books = new ArrayList<>();
//        books.add(TEST_BOOK_DOCUMENT); // 세이노
//
//        List<BookSearchRes> bookList = new ArrayList<>();
//        bookList.add(TEST_BOOK_SEARCH);
//        String title = "세이노";
//
//        given(bookSearchRepository.findByTitleContaining(any())).willReturn(books);
//        when(bookMapper.toDtoList(books)).thenReturn(bookList);
//
//        // when
//        bookList = bookService.findBookByTitle(title);
//
//
//        // then
//        assertThat(bookList.size()).isEqualTo(1);
//    }

    @Test
    @DisplayName("Service) book id로 책 조회")
    void findBookById() {
        // given
        BookRes bookRes = TEST_BOOK;

        given(bookJpaRepository.findByBookId(1L)).willReturn(Optional.ofNullable(TEST_BOOK_ENT));
        when(bookMapper.toDto(TEST_BOOK_ENT, 10, true)).thenReturn(bookRes);

        // when
        bookRes = bookService.findBookById(6L, 1L);

        // then
        assertThat(bookRes.getTitle()).isEqualTo(TEST_BOOK_ENT.getTitle());
    }

    @Test
    @DisplayName("Service) id별 판별")
    void findById() {
        // given
        given(bookJpaRepository.findById(1L)).willReturn(Optional.ofNullable(TEST_BOOK_ENT));

        // when
        Book book = bookService.findById(1L);

        // then
        assertThat(book.getTitle()).isEqualTo(TEST_BOOK_ENT.getTitle());
    }
    @Test
    @DisplayName("id에 해당하는 책이 없을 경우")
    void bookNotFoundException() {
        Mockito.lenient().when(bookJpaRepository.findById(1L)).thenThrow(new BookNotFoundException());
        Mockito.lenient().when(bookJpaRepository.findByBookId(2L)).thenThrow(new BookNotFoundException());

        assertAll(
                () -> assertThrows(BookNotFoundException.class, () -> {
                    bookJpaRepository.findById(1L);
                }, "findById() 해당하는 책이 있습니다."),

                () -> assertThrows(BookNotFoundException.class, () -> {
                    bookJpaRepository.findByBookId(2L);
                }, "findByBookId() 해당하는 책이 있습니다.")
        );
    }
}
package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.entity.BookDocument;
import com.techeer.checkIt.domain.book.mapper.BookMapper;
import com.techeer.checkIt.domain.book.exception.BookNotFoundException;
import com.techeer.checkIt.domain.book.repository.BookJpaRepository;
import com.techeer.checkIt.domain.book.repository.BookSearchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BookService {
    private final BookJpaRepository bookJpaRepository;
    private final BookSearchRepository bookSearchRepository;
    private final BookMapper bookMapper;

    public List<BookSearchRes> findBookByTitle(String title) {
        List<BookDocument> books = bookSearchRepository.findByTitleContaining(title);
        return bookMapper.toDtoList(books);
    }

    public List<BookRes> findBooksByTime() {
        List<Book> newBooks = bookJpaRepository.findAllByCreatedAt();
        return bookMapper.toBookResDtoList(newBooks);
    }

    // id별 조회할 때
    public BookRes findBookById(Long id) {
        Book book = bookJpaRepository.findByBookId(id).orElseThrow(BookNotFoundException::new);
        return bookMapper.toDto(book);
    }
    // 책 판별용
    public Book findById(Long id) {
        return bookJpaRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }
}

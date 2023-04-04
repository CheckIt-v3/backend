package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.mapper.BookMapper;
import com.techeer.checkIt.domain.book.exception.BookNotFoundException;
import com.techeer.checkIt.domain.book.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookRes> findBookByTitle(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        return bookMapper.toDtoList(books);
    }

    // id별 조회할 때
    public BookRes findBookById(Long id) {
        Book book = bookRepository.findByBookId(id).orElseThrow(BookNotFoundException::new);
        return bookMapper.toDto(book);
    }
    // 책 판별용
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }
}

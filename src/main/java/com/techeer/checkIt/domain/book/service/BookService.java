package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.dto.Response.BookResponse;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.exception.BookNotFoundException;
import com.techeer.checkIt.domain.book.repository.BookRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BookService {
    private final BookRepository bookRepository;

    public List<BookResponse> findBookByTitle(String title) {
        return bookRepository.findByTitle(title).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookResponse findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return toDto(book);
    }

    public BookResponse toDto(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .coverImageUrl(book.getCoverImageUrl())
                .build();
    }
}

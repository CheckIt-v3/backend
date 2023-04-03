package com.techeer.checkIt.domain.book.mapper;

import com.techeer.checkIt.domain.book.dto.Response.BookResponse;
import com.techeer.checkIt.domain.book.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public BookResponse toDto(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .coverImageUrl(book.getCoverImageUrl())
                .height(book.getHeight())
                .width(book.getWidth())
                .thickness(book.getThickness())
                .build();
    }
    public List<BookResponse> toDtoList(List<Book> books){
        return books.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

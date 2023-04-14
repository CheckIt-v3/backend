package com.techeer.checkIt.domain.book.mapper;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public BookRes toDto(Book book) {
        return BookRes.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .coverImageUrl(book.getCoverImageUrl())
                .height(book.getHeight())
                .width(book.getWidth())
                .thickness(book.getThickness())
                .build();
    }
    public List<BookRes> toDtoList(List<Book> books){
        return books.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

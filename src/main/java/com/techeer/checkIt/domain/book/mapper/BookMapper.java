package com.techeer.checkIt.domain.book.mapper;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.entity.BookDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public BookRes toDto(Book book, int likes, boolean like_status) {
        return BookRes.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .coverImageUrl(book.getCoverImageUrl())
                .height(book.getHeight())
                .width(book.getWidth())
                .pages(book.getPages())
                .likes(likes)
                .like_status(like_status)
                .build();
    }
    public BookSearchRes toBookSearchDto(BookDocument book) {
        return BookSearchRes.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .coverImageUrl(book.getCoverImageUrl())
                .pages(book.getPages())
                .category(book.getCategory())
                .build();
    }
    public List<BookSearchRes> toDtoList(List<BookDocument> books){
        return books.stream()
                .map(this::toBookSearchDto)
                .collect(Collectors.toList());
    }
}

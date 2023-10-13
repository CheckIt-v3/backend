package com.techeer.checkIt.domain.reading.mapper;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.dto.Response.BookRes;

import com.techeer.checkIt.domain.reading.dto.response.ReadingRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateLastPageAndPercentageRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadingMapper {

    public Reading toEntity(User user, Book book, int lastPage, ReadingStatus status) {
        return Reading.builder()
            .user(user)
            .book(book)
            .lastPage(lastPage)
            .status(status)
            .build();
    }
    public BookRes toDto(Reading reading) {
        return BookRes.builder()
            .id(reading.getBook().getId())
            .title(reading.getBook().getTitle())
            .author(reading.getBook().getAuthor())
            .publisher(reading.getBook().getPublisher())
            .coverImageUrl(reading.getBook().getCoverImageUrl())
            .height(reading.getBook().getHeight())
            .width(reading.getBook().getWidth())
            .pages(reading.getBook().getPages())
            .build();
    }

    // TODO: int likes, boolean likeStatus 을 안 받기 때문에 값 안 가져옴
    public BookRes toDtoByBook(Book book) {
        return BookRes.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .publisher(book.getPublisher())
            .coverImageUrl(book.getCoverImageUrl())
            .height(book.getHeight())
            .width(book.getWidth())
            .pages(book.getPages())
            .build();
    }

    public ReadingRes toReadingList(List<Reading> readings, ReadingStatus status) {
        List<BookRes> bookInfos = readings.stream().map(this::toDto).collect(Collectors.toList());

        return ReadingRes.builder()
                .bookInfos(bookInfos)
                .status(status)
                .build();
    }

    public ReadingRes toReadingListByBook(List<Book> books, ReadingStatus status) {
        List<BookRes> bookInfos = books.stream().map(this::toDtoByBook).collect(Collectors.toList());

        return ReadingRes.builder()
                .bookInfos(bookInfos)
                .status(status)
                .build();
    }

    public UpdateReadingAndReadingVolumeRes toUpdateReadingAndReadingVolumeResDto(Reading reading, ReadingVolume readingVolume){
        return UpdateReadingAndReadingVolumeRes.builder()
            .pages(readingVolume.getTodayPages())
            .lastPage(reading.getLastPage())
            .build();
    }

    public UpdateLastPageAndPercentageRes toUpdateLastPageAndPercentageResDto(Reading reading, double percentage) {
        return UpdateLastPageAndPercentageRes.builder()
            .lastPage(reading.getLastPage())
            .percentage(percentage)
            .build();
    }
}

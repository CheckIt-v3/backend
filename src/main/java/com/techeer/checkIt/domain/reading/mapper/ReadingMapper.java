package com.techeer.checkIt.domain.reading.mapper;

import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.mapper.BookMapper;
import com.techeer.checkIt.domain.reading.dto.response.ReadingRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateLastPageAndPercentageRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.review.entity.Review;
import com.techeer.checkIt.domain.review.mapper.ReviewMapper;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingMapper {

    private final BookMapper bookMapper;
    private final ReviewMapper reviewMapper;

    public Reading toEntity(User user, Book book, int lastPage, ReadingStatus status) {
        return Reading.builder()
            .user(user)
            .book(book)
            .lastPage(lastPage)
            .status(status)
            .build();
    }
    public BookReadingRes toDto(Reading reading) {
        BookReadingRes.BookReadingResBuilder builder = BookReadingRes.builder()
            .id(reading.getBook().getId())
            .title(reading.getBook().getTitle())
            .author(reading.getBook().getAuthor())
            .publisher(reading.getBook().getPublisher())
            .coverImageUrl(reading.getBook().getCoverImageUrl())
            .height(reading.getBook().getHeight())
            .width(reading.getBook().getWidth())
            .pages(reading.getBook().getPages())
            .likes(reading.getBook().getLikeCount())
            .lastPage(reading.getLastPage());

            // 상태별 추가 정보 적용
            if (reading.getStatus() == ReadingStatus.READING) {
                builder.lastPage(reading.getLastPage());
                builder.percentage(Math.round((double) reading.getLastPage() / reading.getBook().getPages() * 100 * 100.0) / 100.0);
            }
        return builder.build();
    }

    public ReadingRes toReadingList(List<Reading> readings, ReadingStatus status, Pageable pageable) {
        Page<BookReadingRes> bookInfos = new PageImpl<>(readings.stream()
                                                                .map(this::toDto)
                                                                .collect(Collectors.toList()), pageable, readings.size());

        return ReadingRes.builder()
                .bookInfos(bookInfos)
                .status(status)
                .build();
    }

    public ReadingRes toReadingListByBook(List<Book> books, ReadingStatus status, Pageable pageable) {
        Page<BookReadingRes> bookInfos = new PageImpl<>(books.stream()
                                                                .map(bookMapper::toDtoByBook)
                                                                .collect(Collectors.toList()), pageable, books.size());

        return ReadingRes.builder()
                .bookInfos(bookInfos)
                .status(status)
                .build();
    }

    public ReadingRes toReadingListByReview(List<Review> reviews, ReadingStatus status, Pageable pageable) {
        Page<BookReadingRes> bookInfos = new PageImpl<>(reviews.stream()
                                                                .map(reviewMapper::toDtoByReview)
                                                                .collect(Collectors.toList()), pageable, reviews.size());

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

package com.techeer.checkIt.domain.reading.mapper;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.reading.entity.Reading;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadingMapper {
    public BookRes toDto(Reading reading) {
        return BookRes.builder()
                .title(reading.getBook().getTitle())
                .author(reading.getBook().getAuthor())
                .publisher(reading.getBook().getPublisher())
                .coverImageUrl(reading.getBook().getCoverImageUrl())
                .height(reading.getBook().getHeight())
                .width(reading.getBook().getWidth())
                .thickness(reading.getBook().getThickness())
                .build();
    }
    public List<BookRes> toDtoList(List<Reading> readings) {
        return readings.stream().map(this::toDto).collect(Collectors.toList());
    }
}

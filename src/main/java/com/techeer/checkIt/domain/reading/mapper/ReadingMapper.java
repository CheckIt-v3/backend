package com.techeer.checkIt.domain.reading.mapper;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.readingVolume.dto.response.SearchReadingVolumesRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.stereotype.Component;

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
    public BookResponse toDto(Reading reading) {
        return BookResponse.builder()
                .title(reading.getBook().getTitle())
                .author(reading.getBook().getAuthor())
                .publisher(reading.getBook().getPublisher())
                .coverImageUrl(reading.getBook().getCoverImageUrl())
                .height(reading.getBook().getHeight())
                .width(reading.getBook().getWidth())
                .thickness(reading.getBook().getThickness())
                .build();
    }
    public List<BookResponse> toDtoList(List<Reading> readings) {
        return readings.stream().map(this::toDto).collect(Collectors.toList());
    }
}

package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.dto.Response.BookResponse;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingRequest;
import com.techeer.checkIt.domain.reading.dto.response.ReadingResponse;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final BookService bookService;

    public void registerReading(User user, Book book, CreateReadingRequest createRequest){
        LocalDateTime date = LocalDateTime.now();
        ReadingStatus status = ReadingStatus.convert(createRequest.getStatus().toUpperCase());
        int lastPage = 0;
        if(status == ReadingStatus.READING) lastPage = createRequest.getLastPage();
        else if(status == ReadingStatus.READ) lastPage = book.getPages();

        Reading reading = Reading.builder()
                .user(user)
                .book(book)
                .date(date)
                .lastPage(lastPage)
                .status(status)
                .build();
        readingRepository.save(reading);
    }

    public List<ReadingResponse> findReadingByStatus(Long userId, ReadingStatus status) {
        List<Reading> readings =readingRepository.findByUserIdAndStatus(userId ,status);

        return readings.stream().map(this::toDto).collect(Collectors.toList());
    }


    public ReadingResponse toDto(Reading reading) {
        return ReadingResponse.builder()
                .book(toBookDto(reading))
                .status(reading.getStatus())
                .build();
    }
    public BookResponse toBookDto(Reading reading) {
        return BookResponse.builder()
                .title(reading.getBook().getTitle())
                .author(reading.getBook().getAuthor())
                .publisher(reading.getBook().getPublisher())
                .coverImageUrl(reading.getBook().getCoverImageUrl())
                .build();
    }
}

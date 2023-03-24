package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingRequest;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class ReadingService {
    private final ReadingRepository readingRepository;

    public void registerReading(User user, Book book, CreateReadingRequest createRequest){
        Date date = new Date();
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
}

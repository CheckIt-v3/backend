package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingRequest;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import com.techeer.checkIt.domain.user.dto.request.UpdateReadingRequestDto;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final ReadingVolumeService readingVolumeService;

    public void registerReading(User user, Book book, CreateReadingRequest createRequest){
        ReadingStatus status = ReadingStatus.convert(createRequest.getStatus().toUpperCase());
        int lastPage = 0;
        if(status == ReadingStatus.READING) lastPage = createRequest.getLastPage();
        else if(status == ReadingStatus.READ) lastPage = book.getPages();

        Reading reading = Reading.builder()
                .user(user)
                .book(book)
                .lastPage(lastPage)
                .status(status)
                .build();
        readingRepository.save(reading);
    }

    public void findReadingByDate(User user, Book book, UpdateReadingRequestDto dto) {
        LocalDate date = LocalDate.now();
        Reading reading = readingRepository.findLastPageByUserAndBook(user,book).orElseThrow(null);
        int pages = dto.getLastPages() - reading.getLastPage(); // 책A 읽은 페이지
        if(readingVolumeService.existsUserAndDate(user,date)) { // 오늘 데이터가 있다면
            ReadingVolume readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(user, date);
            readingVolume.sumTodayPages(pages); // 오늘 데이터에 더하기

        }
        else { // 없다면
            readingVolumeService.registerReadingVolume(user, pages); // 새로 생성
        }
        reading.updateStatus(ReadingStatus.READING);
        reading.updateLastPage(dto.getLastPages()); // reading의 lastpages 갱신
    }
}

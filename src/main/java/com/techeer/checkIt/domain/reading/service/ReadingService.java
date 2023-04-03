package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.dto.Response.BookResponse;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.exception.ReadingNotFoundException;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final ReadingMapper readingMapper;
    private final ReadingVolumeService readingVolumeService;
    private final ReadingMapper readingMapper;
    private final ReadingVolumeMapper readingVolumeMapper;

    public void registerReading(User user, Book book, CreateReadingReq createRequest){
        ReadingStatus status = ReadingStatus.convert(createRequest.getStatus().toUpperCase());
        int lastPage = 0;
        if(status == ReadingStatus.READING) lastPage = createRequest.getLastPage();
        else if(status == ReadingStatus.READ) lastPage = book.getPages();
        Reading reading = readingMapper.toEntity(user,book,lastPage,status);
        readingRepository.save(reading);
    }

    public List<BookResponse> findReadingByStatus(Long userId, ReadingStatus status) {
        List<Reading> readings =readingRepository.findByUserIdAndStatus(userId ,status);
        return readingMapper.toDtoList(readings);
    }
    
    public ReadingVolume updateReadingAndReadingVolume(User user, Book book, UpdateReadingAndReadingVolumeReq dto) {
        ReadingVolume readingVolume = readingVolumeMapper.toEmptyEntity();
        LocalDate date = LocalDate.now();
        Reading reading = readingRepository.findLastPageByUserAndBook(user,book).orElseThrow(ReadingNotFoundException::new);
        int nPage = dto.getLastPage() - reading.getLastPage(); // 책A 읽은 페이지, newPage
        if(readingVolumeService.existsUserAndDate(user,date)) { // 오늘 데이터가 있다면
            readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(user, date);
            readingVolume.sumTodayPages(nPage); // 오늘 데이터에 더하기
        }
        else { // 없다면
            readingVolume = readingVolumeService.registerReadingVolume(user, nPage); // 새로 생성
        }
        reading.updateStatus(ReadingStatus.READING);
        reading.updateLastPage(dto.getLastPage()); // reading의 lastpages 갱신
        return readingVolume;
    }
}

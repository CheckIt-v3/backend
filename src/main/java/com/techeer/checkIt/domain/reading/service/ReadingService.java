package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingStatusReq;
import com.techeer.checkIt.domain.reading.dto.response.UpdateLastPageAndPercentageRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.exception.PageValidationException;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.exception.ReadingNotFoundException;
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
    private final ReadingVolumeMapper readingVolumeMapper;

    public int registerReading(User user, Book book, CreateReadingReq createRequest){
        ReadingStatus status = ReadingStatus.convert(createRequest.getStatus().toUpperCase());
        int lastPage = 0; // status가 UNREAD라면 lastPage가 0으로 들어감, 즉 UNREAD 조건 불필요
        if(status == ReadingStatus.READING) lastPage = createRequest.getLastPage();
        else if(status == ReadingStatus.READ) lastPage = book.getPages();
        Reading reading = readingMapper.toEntity(user, book, lastPage, status);
        readingRepository.save(reading);
        return lastPage;
    }

    public List<BookRes> findReadingByStatus(Long userId, ReadingStatus status) {
        List<Reading> readings =readingRepository.findByUserIdAndStatus(userId ,status);

        return readingMapper.toDtoList(readings);
    }

    public void updateReadingStatus(Long userId, Long bookId, ReadingStatus status, UpdateReadingStatusReq updateStatus) {
        Reading reading = readingRepository.findByUserIdAndBookIdAndStatus(userId, bookId, status).orElseThrow(ReadingNotFoundException::new);
        reading.updateStatus(updateStatus.getStatus());
        if(updateStatus.getStatus() == ReadingStatus.READING)
            reading.updateLastPage(updateStatus.getLastPage());
        else if(updateStatus.getStatus() == ReadingStatus.READ)
            reading.updateLastPage(reading.getBook().getPages());
        else
            reading.updateLastPage(0);
    }

    public UpdateReadingAndReadingVolumeRes updateReadingAndReadingVolume(User user, Book book, UpdateReadingAndReadingVolumeReq updateRequest) {
        ReadingVolume readingVolume = readingVolumeMapper.toEmptyEntity();
        LocalDate date = LocalDate.now();
        Reading reading = readingRepository.findByUserAndBook(user,book).orElseThrow(ReadingNotFoundException::new);
        int nPage = pageValidation(updateRequest,reading,book); // 페이지 유효값 확인
        if(readingVolumeService.existsUserAndDate(user,date)) { // 오늘 데이터가 있다면
            readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(user, date);
            readingVolume.sumTodayPages(nPage); // 오늘 데이터에 더하기
        }
        else { // 없다면
            readingVolume = readingVolumeService.registerReadingVolume(user, nPage); // 새로 생성
        }
        reading.updateStatus(ReadingStatus.READING);
        reading.updateLastPage(updateRequest.getLastPage()); // reading의 lastpages 갱신
        UpdateReadingAndReadingVolumeRes updateReadingAndReadingVolumeRes = readingMapper
                .toUpdateReadingAndReadingVolumeResDto(reading,readingVolume);
        return updateReadingAndReadingVolumeRes;
    }

    public UpdateLastPageAndPercentageRes findReadingByUserAndBook(User user, Book book) {
        Reading reading = readingRepository.findByUserAndBook(user,book).orElseThrow(ReadingNotFoundException::new);
        double percentage = Math.round((double) reading.getLastPage() / book.getPages() * 100 * 100.0) / 100.0;
        UpdateLastPageAndPercentageRes updateLastPageAndPercentageRes = readingMapper
                .toUpdateLastPageAndPercentageResDto(reading, percentage);
        return updateLastPageAndPercentageRes;
    }

    /**
     * 입력받은 페이지의 범위가 정상적인지 판단
     * 1. 현재 읽은 페이지보다 이전 페이지 값이 들어온 경우
     * 2. 전체 페이지 이상의 페이지 값이 들어온 경우
     * 값이 정상이라면 읽은 페이지 수 반환
     */
    public int pageValidation(UpdateReadingAndReadingVolumeReq updateReadingAndReadingVolumeReq, Reading reading, Book book){
        int result = updateReadingAndReadingVolumeReq.getLastPage() - reading.getLastPage(); // 읽은 페이지
        boolean flag = book.getPages() < updateReadingAndReadingVolumeReq.getLastPage(); // 마지막 페이지 초과 확인
        if(result < 0) {
            throw new PageValidationException(result);
        }
        if(flag) {
            throw new PageValidationException(flag);
        }
        return result;
    }
}

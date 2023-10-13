package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.dao.RedisDao;
import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.mapper.BookMapper;
import com.techeer.checkIt.domain.book.repository.BookJpaRepository;
import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingStatusReq;
import com.techeer.checkIt.domain.reading.dto.response.ReadingRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateLastPageAndPercentageRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.reading.exception.PageValidationException;
import com.techeer.checkIt.domain.reading.exception.ReadingDuplicatedException;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.exception.ReadingNotFoundException;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import com.techeer.checkIt.domain.user.entity.User;
import java.util.ArrayList;
import java.util.stream.Collectors;
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
    private final RedisDao redisDao;
    private final BookJpaRepository bookJpaRepository;

    public int registerReading(User user, Book book, CreateReadingReq createRequest){
        ReadingStatus status = ReadingStatus.convert(createRequest.getStatus().toUpperCase());
        boolean flag = readingRepository.existsByUserAndBook(user, book);   // 등록된 책이 이미 있는지 판단
        if (!flag) {
            int lastPage = 0;
            if(status == ReadingStatus.READING) lastPage = createRequest.getLastPage();
            else if(status == ReadingStatus.READ) lastPage = book.getPages();
            Reading reading = readingMapper.toEntity(user, book, lastPage, status);
            readingRepository.save(reading);
            return lastPage;
        }
        else
            throw  new ReadingDuplicatedException();
    }

    public ReadingRes findReadingByStatus(Long userId, ReadingStatus status) {
        if (status.toString().equals("UNREAD")) {
            List<Book> readings = new ArrayList<>();
            String redisUserKey = "U" + userId.toString();
            List<Long> likeBook = redisDao.getValuesList(redisUserKey).stream()
                .map(s -> Long.parseLong(s))
                .collect(Collectors.toList());
            readings =  bookJpaRepository.findByBookIdIn(likeBook);
            return readingMapper.toReadingListByBook(readings, status);
        } else {
            List<Reading> readings = new ArrayList<>();
            readings = readingRepository.findByUserIdAndStatus(userId ,status);
            return readingMapper.toReadingList(readings, status);
        }

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
        Reading reading = readingRepository.findByUserAndBook(user, book).orElseThrow(ReadingNotFoundException::new);
        int nPage = pageValidation(updateRequest, reading, book); // 페이지 유효값 확인
        if(readingVolumeService.existsUserAndDate(user, date)) { // 오늘 데이터가 있다면
            readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(user, date);
            readingVolume.sumTodayPages(nPage); // 오늘 데이터에 더하기
        }
        else { // 없다면
            readingVolume = readingVolumeService.registerReadingVolume(user, nPage); // 새로 생성
        }
        reading.updateStatus(ReadingStatus.READING);
        reading.updateLastPage(updateRequest.getLastPage()); // reading의 lastpages 갱신
        return readingMapper
            .toUpdateReadingAndReadingVolumeResDto(reading, readingVolume);
    }

    public UpdateLastPageAndPercentageRes findReadingByUserAndBook(User user, Book book) {
        Reading reading = readingRepository.findByUserAndBook(user,book).orElseThrow(ReadingNotFoundException::new);
        double percentage = calcPercentage(reading.getLastPage(), book.getPages());
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

    public double calcPercentage(int readPage, int lastPage) {
        return Math.round((double) readPage / lastPage * 100 * 100.0) / 100.0;
    }
}

package com.techeer.checkIt.domain.reading.service;


import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.dto.response.UpdateLastPageAndPercentageRes;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.exception.PageValidationException;
import com.techeer.checkIt.domain.reading.exception.ReadingDuplicatedException;
import com.techeer.checkIt.domain.reading.exception.ReadingNotFoundException;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import com.techeer.checkIt.global.error.ErrorCode;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingStatusReq;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.techeer.checkIt.fixture.BookFixtures.*;
import static com.techeer.checkIt.fixture.ReadingFixtures.*;
import static com.techeer.checkIt.fixture.ReadingVolumeFixtures.*;
import static com.techeer.checkIt.fixture.UserFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import static com.techeer.checkIt.domain.reading.entity.ReadingStatus.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadingServiceTest{
    @InjectMocks
    private ReadingService readingService;
    @Mock
    private ReadingRepository readingRepository;
    @Mock
    private ReadingMapper readingMapper;
    @Mock
    private ReadingVolumeService readingVolumeService;
    @Mock
    private ReadingVolumeMapper readingVolumeMapper;

    @Test
    @DisplayName("마지막 페이지 가져오기 및 퍼센트 계산")
    void findReadingByUserAndBook() {
        when(readingRepository.findByUserAndBook(any(), any())).thenReturn(Optional.of(TEST_READING));

        double percentage = readingService.calcPercentage(TEST_READING.getLastPage(), TEST_BOOK_ENT.getPages());

        UpdateLastPageAndPercentageRes updateLastPageAndPercentageRes = readingMapper
                .toUpdateLastPageAndPercentageResDto(TEST_READING, percentage);

        UpdateLastPageAndPercentageRes newUpdateLastPageAndPercentageRes = readingService.findReadingByUserAndBook(TEST_USER, TEST_BOOK_ENT);

        assertEquals(updateLastPageAndPercentageRes, newUpdateLastPageAndPercentageRes);
    }

    @Test
    @DisplayName("reading의 lastPage와 book의 pages을 입력받아 전체의 몇 퍼센트를 읽었는지 알려준다.")
    void calcPercentage() {
        double percentage = readingService.calcPercentage(TEST_READING.getLastPage(), TEST_BOOK_ENT.getPages());
        double newPercentage = Math.round((double) TEST_READING.getLastPage() / TEST_BOOK_ENT.getPages() * 100 * 100.0) / 100.0;
        assertEquals(
                percentage, newPercentage);
    }

    @Test
    @DisplayName("마지막 페이지 갱신 성공(오늘 자 기록 o)")
    void updateReadingAndReadingVolumeNotFirst() {
        when(readingRepository.findByUserAndBook(any(), any())).thenReturn(Optional.of(TEST_READING));
        when(readingVolumeService.existsUserAndDate(any(), any())).thenReturn(true);
        when(readingVolumeService.findReadingVolumeByUserAndDate(any(), any())).thenReturn(TEST_READINGVOLUME_ENT);

        Reading reading = readingRepository.findByUserAndBook(TEST_USER, TEST_BOOK_ENT).orElseThrow(null);
        LocalDate date = LocalDate.now();
        ReadingVolume readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(TEST_USER, date);

        UpdateReadingAndReadingVolumeRes updateReadingAndReadingVolumeRes = readingMapper.toUpdateReadingAndReadingVolumeResDto(reading, readingVolume);


        UpdateReadingAndReadingVolumeReq updateRequest = UpdateReadingAndReadingVolumeReq.builder()
                .lastPage(100)
                .bookId(1L)
                .build();

        UpdateReadingAndReadingVolumeRes newUpdateReadingAndReadingVolumeRes = readingService.updateReadingAndReadingVolume(TEST_USER, TEST_BOOK_ENT, updateRequest);

        assertEquals(
                updateReadingAndReadingVolumeRes, newUpdateReadingAndReadingVolumeRes);

    }

    @Test
    @DisplayName("마지막 페이지 갱신 성공 (오늘 자 기록 x)")
    void updateReadingAndReadingVolumeFirst() {
        when(readingRepository.findByUserAndBook(any(), any())).thenReturn(Optional.of(TEST_READING));
        when(readingVolumeService.existsUserAndDate(any(), any())).thenReturn(false);
        when(readingVolumeService.findReadingVolumeByUserAndDate(any(), any())).thenReturn(TEST_READINGVOLUME_ENT);

        Reading reading = readingRepository.findByUserAndBook(TEST_USER, TEST_BOOK_ENT).orElseThrow(null);
        LocalDate date = LocalDate.now();
        ReadingVolume readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(TEST_USER, date);

        UpdateReadingAndReadingVolumeRes updateReadingAndReadingVolumeRes = readingMapper.toUpdateReadingAndReadingVolumeResDto(reading, readingVolume);


        UpdateReadingAndReadingVolumeReq updateRequest = UpdateReadingAndReadingVolumeReq.builder()
                .lastPage(100)
                .bookId(1L)
                .build();

        UpdateReadingAndReadingVolumeRes newUpdateReadingAndReadingVolumeRes = readingService.updateReadingAndReadingVolume(TEST_USER, TEST_BOOK_ENT, updateRequest);

        assertEquals(
                updateReadingAndReadingVolumeRes, newUpdateReadingAndReadingVolumeRes);

    }

    @Test
    @DisplayName("페이지 유효값 확인, 책 범위를 벗어난 경우")
    void pageValidationOutOfPage() {
        PageValidationException pageValidationException = assertThrows(PageValidationException.class, () -> {
            readingService.pageValidation(TEST_READINGVOLUME_UPDATE_REQ_OUT_OF_PAGE, TEST_READING, TEST_BOOK_ENT);
        });
        assertEquals(ErrorCode.PAGE_VALIDATION_OUT_OF_PAGE, pageValidationException.getErrorCode());
    }

    @Test
    @DisplayName("페이지 유효값 확인, 마지막으로 읽은 페이지보다 낮은값이 입력된 경우")
    void pageValidationNegativeValue() {
        PageValidationException pageValidationException = assertThrows(PageValidationException.class, () -> {
            readingService.pageValidation(TEST_READINGVOLUME_UPDATE_REQ_NEGATIVE_VALUE, TEST_READING, TEST_BOOK_ENT);
        });
        assertEquals(ErrorCode.PAGE_VALIDATION_NEGATIVE_VALUE, pageValidationException.getErrorCode());
    }

    @Test
    @DisplayName("reading 저장")
    void registerReadingReading() {
        given(readingRepository.save(any())).willReturn(TEST_READING);

        int reading = readingService.registerReading(TEST_USER, TEST_BOOK_ENT, TEST_READING_REQ);
        int read = readingService.registerReading(TEST_USER, TEST_BOOK_ENT, TEST_READING_REQ2);
        int unread = readingService.registerReading(TEST_USER, TEST_BOOK_ENT, TEST_READING_REQ3);


        assertEquals(TEST_READING_REQ.getLastPage(), reading);
        assertEquals(TEST_BOOK_ENT.getPages(), read);
        assertEquals(0, unread);
    }

    @Test
    @DisplayName("reading 저장, 중복 확인")
    void registerReadingReadingDuplicate() {
        given(readingRepository.existsByUserAndBook(any(), any())).willReturn(true);

        ReadingDuplicatedException readingDuplicatedException = assertThrows(ReadingDuplicatedException.class, () -> {
            readingService.registerReading(TEST_USER, TEST_BOOK_ENT, TEST_READING_REQ);
        });
        assertEquals(ErrorCode.READING_DUPLICATED_ERROR, readingDuplicatedException.getErrorCode());

    }

//    @Test
//    @DisplayName("Service) 상태 별 책 조회")
//    void findReadingByStatus() {
//        // given
//        List<Reading> readings = new ArrayList<>();
//        readings.add(TEST_READING);
//        readings.add(TEST_READING2);
//
//        List<BookRes> bookListByStatus  = new ArrayList<>();
//        bookListByStatus.add(TEST_BOOK);
//        bookListByStatus.add(TEST_BOOK2);
//
//        given(readingRepository.findByUserIdAndStatus(1L, READING)).willReturn(readings);
//        when(readingMapper.toDtoList(readings)).thenReturn(bookListByStatus);
//
//        // when
//        bookListByStatus  = readingService.findReadingByStatus(1L, READING);
//
//        // then
//        assertThat(bookListByStatus.size()).isEqualTo(2);
//    }

    @Test
    @DisplayName("Service) 독서 상태 변경")
    void updateReadingStatus() {
        // given
        UpdateReadingStatusReq updateStatusToRead = TEST_UPDATE_READ_REQ;       // READ로 변경
        UpdateReadingStatusReq updateStatusToReading = TEST_UPDATE_READING_REQ; // READIING으로 변경
        UpdateReadingStatusReq updateStatusToUnread = TEST_UPDATE_UNREAD_REQ;   // UNREAD로 변경

        given(readingRepository.findByUserIdAndBookIdAndStatus(any(), any(), any())).willReturn(Optional.ofNullable(TEST_READING));

        // when
        readingService.updateReadingStatus(1L, 1L, READING, updateStatusToRead);
        readingService.updateReadingStatus(1L, 1L, READ, updateStatusToReading);
        readingService.updateReadingStatus(1L, 1L, READING, updateStatusToUnread);


        // then
        assertThat(updateStatusToRead.getLastPage()).isEqualTo(TEST_READING.getBook().getPages());
        assertThat(updateStatusToReading.getLastPage()).isEqualTo(130);
        assertThat(updateStatusToUnread.getLastPage()).isEqualTo(0);
    }
    @Test
    @DisplayName("사용자의 내서재에 책이 없는 경우")
    void bookNotFoundException() {
        Mockito.lenient().when(readingRepository.findByUserIdAndBookIdAndStatus(1L, 2L, READING)).thenThrow(new ReadingNotFoundException());

        assertThrows(ReadingNotFoundException.class, () -> {
            readingRepository.findByUserIdAndBookIdAndStatus(1L, 2L, READING);
        }, "내 서재에 책이 있습니다.");
    }
}
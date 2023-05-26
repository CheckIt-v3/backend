package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.exception.ReadingNotFoundException;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOKENT;
import static com.techeer.checkIt.fixture.ReadingFixtures.TEST_READING;
import static com.techeer.checkIt.fixture.ReadingVolumeFixtures.TEST_READINGVOLUME_ENT;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadingServiceTest {
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
    @DisplayName("user와 book을 입력받아 전체의 몇 퍼센트를 읽었는지 알려준다.")
    void findReadingByUserAndBook() {
        when(readingRepository.findByUserAndBook(any(),any())).thenReturn(Optional.of(TEST_READING));

        double percentage = readingService.findReadingByUserAndBook(TEST_USER,TEST_BOOKENT);
        double newPercentage = Math.round(TEST_READING.getLastPage() / (double) (TEST_BOOKENT.getPages() / 100) * 100.0) / 100.0;
        assertEquals(
        percentage, newPercentage);
    }

    @Test
    @DisplayName("마지막 페이지 갱신 성공")
    void updateReadingAndReadingVolume() {
        when(readingRepository.findByUserAndBook(any(),any())).thenReturn(Optional.of(TEST_READING));
        when(readingVolumeService.existsUserAndDate(any(), any())).thenReturn(true);
        when(readingVolumeService.findReadingVolumeByUserAndDate(any(), any())).thenReturn(TEST_READINGVOLUME_ENT);

        Reading reading = readingRepository.findByUserAndBook(TEST_USER,TEST_BOOKENT).orElseThrow(null);
        LocalDate date = LocalDate.now();
        ReadingVolume readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(TEST_USER, date);

        UpdateReadingAndReadingVolumeRes updateReadingAndReadingVolumeRes = readingMapper
                .toUpdateReadingAndReadingVolumeResDto(reading, readingVolume);


        UpdateReadingAndReadingVolumeReq updateRequest = UpdateReadingAndReadingVolumeReq.builder()
                .lastPage(100)
                .bookId(1L)
                .build();

        UpdateReadingAndReadingVolumeRes newUpdateReadingAndReadingVolumeRes = readingService.updateReadingAndReadingVolume(TEST_USER, TEST_BOOKENT, updateRequest);

        assertEquals(
                updateReadingAndReadingVolumeRes, newUpdateReadingAndReadingVolumeRes);

    }

}
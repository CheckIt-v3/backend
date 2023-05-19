package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.exception.ReadingNotFoundException;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private Book book = TEST_BOOKENT;


    @Test
    @DisplayName("user와 book을 입력받아 전체의 몇 퍼센트를 읽었는지 알려준다.")
    void findReadingByUserAndBook() {
        when(readingRepository.findByUserAndBook(any(),any())).thenReturn(Optional.of(TEST_READING));
        double percentage = Math.round(TEST_READING.getLastPage() / (double) (TEST_BOOKENT.getPages() / 100) * 100.0) / 100.0;
        Reading reading = readingRepository.findByUserAndBook(TEST_USER,TEST_BOOKENT).orElseThrow(ReadingNotFoundException::new);
        double newPercentage = Math.round(reading.getLastPage() / (double) (TEST_BOOKENT.getPages() / 100) * 100.0) / 100.0;
        assertEquals(
        percentage, newPercentage);
    }

    @Test
    @DisplayName("a")
    void updateReadingAndReadingVolume() {
        Mockito.lenient().when(readingRepository.findByUserAndBook(any(),any())).thenReturn(Optional.of(TEST_READING));
        Mockito.lenient().when(readingVolumeService.existsUserAndDate(any(), any())).thenReturn(true);
        Mockito.lenient().when(readingVolumeService.findReadingVolumeByUserAndDate(any(), any())).thenReturn(TEST_READINGVOLUME_ENT);
    }

}
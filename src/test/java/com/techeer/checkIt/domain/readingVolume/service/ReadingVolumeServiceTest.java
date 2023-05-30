package com.techeer.checkIt.domain.readingVolume.service;

import com.techeer.checkIt.domain.readingVolume.exception.ReadingVolumeNotFoundException;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.repository.ReadingVolumeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReadingVolumeServiceTest {
    @InjectMocks
    private ReadingVolumeService readingVolumeService;

    @Mock
    private ReadingVolumeRepository readingVolumeRepository;
    @Mock
    private ReadingVolumeMapper readingVolumeMapper;

    @Test
    @DisplayName("해당 날짜의 독서추세를 찾을 수 없는 경우")
    void readingVolumeNotFoundException() {
        LocalDate date = LocalDate.now();
        Mockito.lenient().when(readingVolumeRepository.findByUserAndDate(TEST_USER, date)).thenThrow(new ReadingVolumeNotFoundException());

        assertThrows(ReadingVolumeNotFoundException.class, () -> {
            readingVolumeRepository.findByUserAndDate(TEST_USER, date);
        }, "해당 날짜의 독서이력이 있습니다.");
    }
}
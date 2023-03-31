package com.techeer.checkIt.domain.readingVolume.service;

import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.repository.ReadingVolumeRepository;
import com.techeer.checkIt.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingVolumeService {
    private final ReadingVolumeRepository readingVolumeRepository;

    public ReadingVolume registerReadingVolume(User user, int pages){
        LocalDate date = LocalDate.now();
        ReadingVolume readingVolume = ReadingVolume.builder()
                .user(user)
                .date(date)
                .todayPages(pages)
                .build();
        return readingVolumeRepository.save(readingVolume);
    }

    public ReadingVolume findReadingVolumeByUserAndDate(User user, LocalDate date) {
        return readingVolumeRepository.findReadingVolumeByUserAndDate(user, date).orElseThrow();
    }

    public boolean existsUserAndDate(User user, LocalDate date) {
        return readingVolumeRepository.existsByUserAndDate(user, date);
    }
}

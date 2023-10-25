package com.techeer.checkIt.domain.readingVolume.service;

import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import com.techeer.checkIt.domain.readingVolume.dto.response.SearchReadingVolumesRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.exception.ReadingVolumeNotFoundException;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.repository.ReadingVolumeRepository;
import com.techeer.checkIt.domain.user.entity.User;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingVolumeService {
    private final ReadingVolumeRepository readingVolumeRepository;
    private final ReadingVolumeMapper readingVolumeMapper;


    public List<SearchReadingVolumesRes> findReadingVolumesByUserAndDateBetween(User user, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate end = LocalDate.parse(date, formatter);
        LocalDate start = end.minusDays(6);
        List<ReadingVolume> readingVolumes =
                readingVolumeRepository.findByUserAndDateBetween(user,start,end);
        return readingVolumeMapper.toDtoList(readingVolumes);
    }

    public ReadingVolume registerReadingVolume(User user, int pages){
        LocalDate date = LocalDate.now();
        ReadingVolume readingVolume = readingVolumeMapper.toEntity(user,date,pages);
        return readingVolumeRepository.save(readingVolume);
    }

    public ReadingVolume findReadingVolumeByUserAndDate(User user, LocalDate date) {
        return readingVolumeRepository.findByUserAndDate(user, date).orElseThrow(ReadingVolumeNotFoundException::new);
    }

    public boolean existsUserAndDate(User user, LocalDate date) {
        return readingVolumeRepository.existsByUserAndDate(user, date);
    }
}

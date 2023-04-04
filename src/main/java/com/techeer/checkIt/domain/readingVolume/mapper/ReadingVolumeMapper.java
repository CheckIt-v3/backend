package com.techeer.checkIt.domain.readingVolume.mapper;

import com.techeer.checkIt.domain.readingVolume.dto.response.SearchReadingVolumesRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadingVolumeMapper {

    public ReadingVolume toEmptyEntity() {
        return ReadingVolume.builder().build();
    }

    public ReadingVolume toEntity(User user, LocalDate date, int pages) {
        return ReadingVolume.builder()
                .user(user)
                .date(date)
                .todayPages(pages)
                .build();
    }

    public SearchReadingVolumesRes toDto(ReadingVolume readingVolume) {
        return SearchReadingVolumesRes.builder()
                .date(readingVolume.getDate())
                .page(readingVolume.getTodayPages())
                .build();
    }
    public List<SearchReadingVolumesRes> toDtoList(List<ReadingVolume> readingVolumes){
        return readingVolumes.stream().map(this::toDto).collect(Collectors.toList());
    }
}

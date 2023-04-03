package com.techeer.checkIt.domain.readingVolume.mapper;

import com.techeer.checkIt.domain.readingVolume.dto.response.SearchReadingVolumesRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadingVolumeMapper {

    public ReadingVolume toEmptyEntity() {
        return ReadingVolume.builder().build();
    }

    public SearchReadingVolumesRes toDto(ReadingVolume readingVolume) {
        return SearchReadingVolumesRes.builder()
                .date(readingVolume.getDate())
                .page(readingVolume.getTodayPages())
                .build();
    }
    public List<SearchReadingVolumesRes> toDtoList(List<ReadingVolume> readingVolumeList){
        return readingVolumeList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
